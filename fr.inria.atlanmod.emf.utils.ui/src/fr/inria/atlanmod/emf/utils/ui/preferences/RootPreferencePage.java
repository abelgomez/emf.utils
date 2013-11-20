package fr.inria.atlanmod.emf.utils.ui.preferences;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Collections;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.osgi.util.NLS;
import org.eclipse.pde.internal.runtime.spy.SpyIDEUtil;
import org.eclipse.pde.internal.ui.editor.schema.SchemaEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import fr.inria.atlanmod.emf.utils.ui.AtlanmodUiUtilsPlugin;

public class RootPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
	
	private static final String CUSTOM_EMF_REGISTRY = "fr.inria.atlanmod.emf.packageregistry";
	private static final String PDE_RUNTIME_PLUGIN_ID = "org.eclipse.pde.runtime";
	private static final String PDE_UI_PLUGIN_ID = "org.eclipse.pde.ui";

	public RootPreferencePage() {
	}

	public RootPreferencePage(String title) {
		super(title);
	}

	public RootPreferencePage(String title, ImageDescriptor image) {
		super(title, image);
	}

	@Override
	public void init(IWorkbench workbench) {
		noDefaultAndApplyButton();
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());

		Composite infoComposite = new Composite(composite, SWT.NONE);
		infoComposite.setLayout(new GridLayout(2, false));
		infoComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout rowLayout = new GridLayout();
		rowLayout.marginWidth = 0;
		rowLayout.marginHeight = 0;
		
		Label imageLabel = new Label(infoComposite, SWT.NONE);
		imageLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		imageLabel.setImage(JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_WARNING));
		
		Link infoLink = new Link(infoComposite, SWT.NONE);
		GridData infoLayoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		infoLayoutData.widthHint = 500;
		infoLink.setLayoutData(infoLayoutData);
		infoLink.setText(buildLinkText());
		
		if (isPdeAvailable()) {
			addListener(infoLink);
		}

		Label logoLabel = new Label(composite, SWT.NONE);
		logoLabel.setImage(AtlanmodUiUtilsPlugin.getDefault().getImageRegistry().get(AtlanmodUiUtilsPlugin.OTHER_LOGO_ATLANMOD));
		logoLabel.setLayoutData(new GridData(SWT.END, SWT.END, false, false));

		if (Platform.getBundle(CUSTOM_EMF_REGISTRY) != null) {
			infoComposite.setVisible(true);
		} else {
			infoComposite.setVisible(false);
		}
		
		return composite;
	}

	private String buildLinkText() {
		StringBuilder builder = new StringBuilder();
								builder.append("The AtlanMod EMF EPackage custom registry (");
		if (isPdeAvailable())
								builder.append("<a href=\"CUSTOM_REGISTRY_CLASS\">");
								builder.append("ObservableEPackageRegistryImpl");
		if (isPdeAvailable())
								builder.append("</a>");
								builder.append(") is contributed using the ");
		if (isPdeAvailable())
								builder.append("<a href=\"EXTENSION_POINT\">");
								builder.append("org.eclipse.emf.ecore.package_registry_implementation");
		if (isPdeAvailable())
								builder.append("</a>");
								builder.append(" extension point and overrides the default implementation (");
		if (isPdeAvailable())
								builder.append("<a href=\"DEFAULT_REGISTRY\">");
								builder.append("EPackageRegistryImpl");
		if (isPdeAvailable())
								builder.append("</a>");
								builder.append(").\n\n");
								builder.append("According to the documentation, there can be at most one use of this extension point in the environment. Multiple ");
								builder.append("uses will be logged as errors. This setting affects the whole environment and should be used with caution.\n\n");
								builder.append("If you detect an erratic behaviour of the EMF registry you can disable the ");
		if (isPdeAvailable())
								builder.append("<a href=\"CUSTOM_REGISTRY_PLUGIN\">");
								builder.append("fr.inria.atlanmod.emf.packageregistry");
		if (isPdeAvailable())
								builder.append("</a>");
								builder.append(" plugin to restore the default implementation.");
		return builder.toString();
	}

	private void addListener(Link infoLabel) {
		infoLabel.addListener (SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				try {
					switch (event.text) {
						case "CUSTOM_REGISTRY_CLASS":
							SpyIDEUtil.openClass("fr.inria.atlanmod.emf.packageregistry", "fr.inria.atlanmod.emf.packageregistry.impl.ObservableEPackageRegistryImpl");
							break;
						case "EXTENSION_POINT":
							URL url = FileLocator.find(Platform.getBundle("org.eclipse.emf.ecore"), new Path("schema/package_registry_implementation.exsd"), Collections.emptyMap());
							url = FileLocator.resolve(url);
							// The url is unencoded, so we can treat it like a path, splitting it based on the jar suffix '!'
							String stringUrl = url.getPath();
							int jarSuffix = stringUrl.indexOf('!');
							String fileUrl = stringUrl.substring(0, jarSuffix);
							URI uri = URIUtil.toURI(new URL(fileUrl));
							File jarFile = URIUtil.toFile(uri);
							String schemaEntryName = stringUrl.substring(jarSuffix + 1);
							if (schemaEntryName.startsWith("/")) { //$NON-NLS-1$
								schemaEntryName = schemaEntryName.substring(1);
							}
							// Open the schema in a new editor
							SchemaEditor.openSchema(jarFile, schemaEntryName);
							break;
						case "DEFAULT_REGISTRY":
							SpyIDEUtil.openClass("org.eclipse.emf.ecore", "org.eclipse.emf.ecore.impl.EPackageRegistryImpl");
							break;
						case "CUSTOM_REGISTRY_PLUGIN":
							SpyIDEUtil.openBundleManifest("fr.inria.atlanmod.emf.packageregistry");
							break;
						default:
							break;
					}
				} catch (Throwable t) {
					MessageDialog.openError(getShell(), "Error", 
							NLS.bind("Unable to open link in new editor ({0})", t.toString()));
				}
			}
		});
	}
	
	private boolean isPdeAvailable() {
		return (Platform.getBundle(PDE_RUNTIME_PLUGIN_ID) != null) 
				&& (Platform.getBundle(PDE_UI_PLUGIN_ID) != null);
	}
}
