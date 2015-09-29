package io.github.abelgomez.emf.utils;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.common.util.URI;
import org.osgi.framework.BundleContext;

import io.github.abelgomez.emf.utils.service.RegistryManager;
import io.github.abelgomez.emf.utils.service.impl.RegistryManagerImpl;


public class EMFUtilsPlugin extends Plugin {

	private static EMFUtilsPlugin instance;

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
	
	public static EMFUtilsPlugin getDefault() {
		return instance;
	}
	
	public RegistryManager getRegistryManager() {
		return registryService;
	}
	
}


