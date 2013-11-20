package fr.inria.atlanmod.emf.utils.ui;

import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.statushandlers.StatusManager;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class AtlanmodUiUtilsPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "fr.inria.atlanmod.emf.utils.ui"; //$NON-NLS-1$

	public static final String EVIEW16_REFRESH_ICON = "EVIEW16_REFRESH_ICON";
	public static final String OTHER_LOGO_ATLANMOD = "OTHER_LOGO_ATLANMOD";

	// The shared instance
	private static AtlanmodUiUtilsPlugin plugin;
	
	ILogListener logListener = new ILogListener() {
		@Override
		public void logging(IStatus status, String plugin) {
			StatusManager.getManager().handle(status, StatusManager.BLOCK);
		}
	};

	/**
	 * The constructor
	 */
	public AtlanmodUiUtilsPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		getLog().addLogListener(logListener);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		getLog().removeLogListener(logListener);
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static AtlanmodUiUtilsPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	@Override
	protected void initializeImageRegistry(ImageRegistry reg) {
		super.initializeImageRegistry(reg);
		reg.put(EVIEW16_REFRESH_ICON, getImageDescriptor("icons/eview16/refresh_icon.gif"));
		reg.put(OTHER_LOGO_ATLANMOD, getImageDescriptor("icons/other/logo_atlanmod.png"));
	}
}
