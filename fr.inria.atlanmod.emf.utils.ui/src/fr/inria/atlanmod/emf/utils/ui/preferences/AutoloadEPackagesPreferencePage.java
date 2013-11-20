package fr.inria.atlanmod.emf.utils.ui.preferences;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import fr.inria.atlanmod.emf.utils.AtlanmodUtilsPlugin;
import fr.inria.atlanmod.emf.utils.dynregistry.RegistryEntry;
import fr.inria.atlanmod.emf.utils.dynregistry.provider.DynregistryItemProviderAdapterFactory;
import fr.inria.atlanmod.emf.utils.service.NotDanglingResourceException;
import fr.inria.atlanmod.emf.utils.service.RegistryManager;
import fr.inria.atlanmod.emf.utils.service.UnknownResourceException;
import fr.inria.atlanmod.emf.utils.ui.AtlanmodUiUtilsPlugin;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class AutoloadEPackagesPreferencePage
	extends PreferencePage
	implements IWorkbenchPreferencePage {

	private List<RegistryEntry> entries;
	private List<RegistryEntry> entriesToDelete;

	private TableViewer viewer;
	private Button unregButton;
	private Button unregAllButton;
	private RegistryManager registryManager;

	public AutoloadEPackagesPreferencePage() {
		super();
		setPreferenceStore(AtlanmodUiUtilsPlugin.getDefault().getPreferenceStore());
		setDescription("Manage which resources containing an Ecore EPackage should be auto-loaded");
		registryManager = AtlanmodUtilsPlugin.getDefault().getRegistryManager();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
		initialize();
	}

	private void initialize() {
		entries = new ArrayList<>(registryManager.getAutoloadEntries());
		entriesToDelete = new ArrayList<>();
	}


	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));

		Table table = new Table(composite, SWT.BORDER);
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		layoutData.widthHint = 100;
		table.setLayoutData(layoutData);
		viewer = new TableViewer(table);
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(new AdapterFactoryLabelProvider(new DynregistryItemProviderAdapterFactory()));
		viewer.setInput(entries);
		
		Composite buttonsComposite = new Composite(composite, SWT.NONE);
		buttonsComposite.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, false, false));
		buttonsComposite.setLayout(new FillLayout(SWT.VERTICAL));
		
		unregButton = new Button(buttonsComposite, SWT.PUSH);
		unregButton.setText("&Unregister");
		unregButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
				RegistryEntry entry = (RegistryEntry) selection.getFirstElement();
				entriesToDelete.add(entry);
				entries.remove(entry);
				viewer.refresh();
			}
		});
		
		unregAllButton = new Button(buttonsComposite, SWT.PUSH);
		unregAllButton.setText("Unregister &All");
		unregAllButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				entriesToDelete.addAll(entries);
				entries.clear();
				viewer.refresh();
			}
		});

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				unregButton.setEnabled(!event.getSelection().isEmpty());
			}
		});
		
		return composite;
	}
	
	
	
	@Override
	protected void performDefaults() {
		super.performDefaults();
		initialize();
		viewer.refresh();
	}
	
	@Override
	public boolean performOk() {
		for (RegistryEntry entry : entriesToDelete) {
			EPackage ePackage;
			try {
				try {
					ePackage = registryManager.loadEPackage(entry.getUri());
					registryManager.unregisterEPackage(ePackage);
				} catch (UnknownResourceException e) {
					registryManager.removeDanglingAutoloadEntry(entry.getUri());
				}
			} catch (NotDanglingResourceException | IOException e) {
				return false;
			}
		}
		return true;
	}
}