package fr.inria.atlanmod.emf.utils.ui.views;


import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.emf.common.ui.dialogs.ResourceDialog;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import fr.inria.atlanmod.emf.utils.AtlanmodUtilsPlugin;
import fr.inria.atlanmod.emf.utils.ui.AtlanmodUiUtilsPlugin;
import fr.inria.atlanmod.emf.utils.ui.ExceptionStatus;
import fr.inria.atlanmod.emf.utils.ui.dialogs.EcoreResourceDialog;
import fr.inria.atlanmod.emf.utils.ui.editors.EPackageEditorInput;
import fr.inria.atlanmod.emf.utils.ui.editors.ReadOnlyEcoreEditor;


public class PackageRegistryView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "fr.inria.atlanmod.emf.utils.ui.views.PackageRegistryView";

	private Shell shell;

	private Registry registry;

	private TableViewer viewer;

	private Action refreshAction;
	private Action registerAction;
	private Action unregisterAction;
	private Action doubleClickAction;

	private Observer registryObserver = new Observer() {
		@Override
		public void update(Observable o, Object arg) {
			shell.getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					viewer.refresh();	
				}
			});
		}
	};


	/**
	 * The constructor.
	 */
	public PackageRegistryView() {
		registry = EPackage.Registry.INSTANCE;
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		shell = parent.getShell();
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new MapContentProvider());
		viewer.setLabelProvider(new RegistryLabelProvider());
		viewer.setSorter(new ViewerSorter());
		viewer.setInput(registry);
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
		if (registry instanceof Observable) {
			Observable observable = (Observable) registry;
			observable.addObserver(registryObserver);
		}
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				unregisterAction.setEnabled(!event.getSelection().isEmpty());
			}
		});
		viewer.refresh();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				PackageRegistryView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(refreshAction);
		manager.add(new Separator());
		manager.add(registerAction);
		manager.add(unregisterAction);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(unregisterAction);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(refreshAction);
		manager.add(new Separator());
		manager.add(registerAction);
		manager.add(unregisterAction);
	}

	private void makeActions() {
		refreshAction = new Action() {
			public void run() {
				viewer.refresh();
			}
		};
		refreshAction.setText("Refresh");
		refreshAction.setToolTipText("Reload EPackages from the EMF registry");
		refreshAction.setImageDescriptor(AtlanmodUiUtilsPlugin.getDefault().getImageRegistry().getDescriptor(
				AtlanmodUiUtilsPlugin.EVIEW16_REFRESH_ICON));
		
		registerAction = new Action() {
			public void run() {
				ResourceDialog dialog = new EcoreResourceDialog(shell, "Select an Ecore Resource", SWT.OPEN | SWT.MULTI);
				if (dialog.open() == ResourceDialog.OK) {
					for (URI uri : dialog.getURIs()) {
						try {
							EPackage ePackage = AtlanmodUtilsPlugin.getDefault().getRegistryManager().loadEPackage(uri);
							AtlanmodUtilsPlugin.getDefault().getRegistryManager().registerEPackage(ePackage, true);
						} catch (Exception e) {
							AtlanmodUiUtilsPlugin.getDefault().getLog().log(ExceptionStatus.createExceptionStatus(e));	
						}
					}
			 	}
			}
		};
		registerAction.setText("Register");
		registerAction.setToolTipText("Adds a new EPackage to the EMF EPackages registry");
		registerAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_OBJ_ADD));

		unregisterAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				if (!selection.isEmpty()) {
					Iterator<?> it = ((IStructuredSelection) selection).iterator();
					while (it.hasNext()) {
						Entry<?, ?> obj = (Entry<?, ?>) it.next();
						try {
							URI nsUri = URI.createURI((String) obj.getKey());
							EPackage ePackage = AtlanmodUtilsPlugin.getDefault().getRegistryManager().getEPackage(nsUri);
							AtlanmodUtilsPlugin.getDefault().getRegistryManager().unregisterEPackage(ePackage);
						} catch (Exception e) {
							AtlanmodUiUtilsPlugin.getDefault().getLog().log(ExceptionStatus.createExceptionStatus(e));	
						}
					}
				}
			}
		};
		unregisterAction.setText("Unregister");
		unregisterAction.setToolTipText("Deletes the entry from the EMF EPackages registry");
		unregisterAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_ELCL_REMOVE));

		
		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Entry<?, ?> obj = (Entry<?, ?>) ((IStructuredSelection)selection).getFirstElement();
				if (obj.getKey() instanceof String) {
					String nsURI = (String) obj.getKey();
					IWorkbench workbench = PlatformUI.getWorkbench();
		            IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();
					try {
						page.openEditor(
								new EPackageEditorInput(registry.getEPackage(nsURI)),
								ReadOnlyEcoreEditor.ID);
					} catch (PartInitException e) {
						AtlanmodUiUtilsPlugin.getDefault().getLog().log(ExceptionStatus.createExceptionStatus(e));
					}
				}
			}
		};
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
	
	@Override
	public void dispose() {
		if (registry instanceof Observable) {
			Observable observable = (Observable) registry;
			observable.deleteObserver(registryObserver);
		}
		super.dispose();
	}
}