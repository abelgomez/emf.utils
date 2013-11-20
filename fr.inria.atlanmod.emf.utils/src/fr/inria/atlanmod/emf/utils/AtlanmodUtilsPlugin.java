package fr.inria.atlanmod.emf.utils;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.common.util.URI;
import org.osgi.framework.BundleContext;

import fr.inria.atlanmod.emf.utils.service.RegistryManager;
import fr.inria.atlanmod.emf.utils.service.impl.RegistryManagerImpl;


public class AtlanmodUtilsPlugin extends Plugin {

	private static AtlanmodUtilsPlugin instance;

	private static String REGISTRY_PATH = "dynregistry.xmi";

	private RegistryManagerImpl registryService = null;
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		super.start(bundleContext);
		instance = this;
		URI registryUri = URI.createFileURI(getDefault().getStateLocation().append(REGISTRY_PATH).toString());
		registryService = new RegistryManagerImpl(registryUri);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		registryService.unload();
		registryService = null;
		instance = null;
		super.stop(bundleContext);
	}
	
	public static AtlanmodUtilsPlugin getDefault() {
		return instance;
	}
	
	public RegistryManager getRegistryManager() {
		return registryService;
	}
	
}


