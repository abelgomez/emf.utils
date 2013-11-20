package fr.inria.atlanmod.emf.packageregistry;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class ObservableEPackageRegistryPlugin implements BundleActivator {

	public static String PLUGIN_ID = "fr.inria.atlanmod.emf.packageregistry";
	
	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		ObservableEPackageRegistryPlugin.context = bundleContext;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		ObservableEPackageRegistryPlugin.context = null;
	}

}
