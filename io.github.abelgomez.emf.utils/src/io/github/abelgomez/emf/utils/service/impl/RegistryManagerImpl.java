package io.github.abelgomez.emf.utils.service.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import io.github.abelgomez.emf.utils.dynregistry.DynregistryFactory;
import io.github.abelgomez.emf.utils.dynregistry.Registry;
import io.github.abelgomez.emf.utils.dynregistry.RegistryEntry;
import io.github.abelgomez.emf.utils.service.NotDanglingResourceException;
import io.github.abelgomez.emf.utils.service.NotPersistedResourceException;
import io.github.abelgomez.emf.utils.service.RegistryManager;
import io.github.abelgomez.emf.utils.service.UnknownEPackageException;
import io.github.abelgomez.emf.utils.service.UnknownResourceException;

public class RegistryManagerImpl implements RegistryManager {

	ResourceSet resourceSet;
	Resource autoloadResource = null;
	Registry autoloadRegistry = null;

	public RegistryManagerImpl(URI registryUri) throws IOException {
		resourceSet = new ResourceSetImpl();
		loadRegistry(registryUri);
		registerResources();
	}

	private void loadRegistry(URI registryUri) throws IOException {
		try {
			autoloadResource = resourceSet.getResource(registryUri, true);
			autoloadRegistry = (Registry) autoloadResource.getContents().get(0);
			autoloadRegistry = (Registry) autoloadResource.getContents().get(0);
		} catch (Exception e) {
			autoloadResource = resourceSet.createResource(registryUri);
			autoloadRegistry = DynregistryFactory.eINSTANCE.createRegistry();
			autoloadResource.getContents().add(autoloadRegistry);
			autoloadResource.save(Collections.emptyMap());
		}
	}
	
	private void registerResources() {
		for (RegistryEntry entry : autoloadRegistry.getEntries()) {
			EPackage ePackage;
			try {
				ePackage = loadEPackage(entry.getUri());
				EPackage.Registry.INSTANCE.put(ePackage.getNsURI().toString(), ePackage);
			} catch (Exception e) {
				// Continue silently if the resource can't be loaded
			}
		}
	}
	
	private RegistryEntry findAutoloadRegistryEntry(URI resourceUri) {
		for (RegistryEntry entry : autoloadRegistry.getEntries()) {
			if (entry.getUri().equals(resourceUri)) {
				return entry;
			}
		}
		return null;
	}
	
	@Override
	public List<RegistryEntry> getAutoloadEntries() {
		return Collections.unmodifiableList(autoloadRegistry.getEntries());
	}

	@Override
	public EPackage getEPackage(URI nsUri) throws UnknownEPackageException {
		EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(nsUri.toString());
		if (ePackage == null)
			throw new UnknownEPackageException(nsUri);
		return ePackage;
	}

	@Override
	public EPackage loadEPackage(URI resourceUri) throws UnknownResourceException, IOException {
		EObject eObject = null;
		try {
			Resource resource = new ResourceSetImpl().getResource(resourceUri, true);
			resource.load(Collections.emptyMap());
			eObject = resource.getContents().get(0);
		} catch (Exception e) {
			throw new UnknownResourceException(resourceUri, e);
		}
		if (eObject instanceof EPackage) {
			return (EPackage) eObject;
		} else {
			throw new UnknownResourceException(resourceUri);
		}
	}

	@Override
	public void registerEPackage(EPackage ePackage) {
		EPackage.Registry.INSTANCE.put(ePackage.getNsURI().toString(), ePackage);
	}

	@Override
	public void registerEPackage(EPackage ePackage, boolean autoload) throws NotPersistedResourceException, IOException {
		registerEPackage(ePackage);
		if (autoload) {
			URI resourceUri = ePackage.eResource().getURI();
			try {
				new ResourceSetImpl().getResource(resourceUri, true);
			} catch (Exception e) {
				throw new NotPersistedResourceException(resourceUri);
			}
			if (findAutoloadRegistryEntry(ePackage.eResource().getURI()) == null) {  
				RegistryEntry entry = DynregistryFactory.eINSTANCE.createRegistryEntry();
				entry.setUri(resourceUri);
				autoloadRegistry.getEntries().add(entry);
				autoloadResource.save(Collections.emptyMap());
			}
		}
	}

	@Override
	public void unregisterEPackage(EPackage ePackage) throws IOException {
		RegistryEntry registryEntry = findAutoloadRegistryEntry(ePackage.eResource().getURI());
		if (registryEntry != null) {
			autoloadRegistry.getEntries().remove(registryEntry);
			autoloadResource.save(Collections.emptyMap());
		}
		EPackage.Registry.INSTANCE.remove(ePackage.getNsURI().toString());
		ePackage.eResource().unload();
	}

	@Override
	public void removeDanglingAutoloadEntry(URI resourceUri) throws IOException, NotDanglingResourceException {
		try {
			// Let's check if the resource is already dangling
			loadEPackage(resourceUri);
			throw new NotDanglingResourceException(resourceUri);
		} catch (UnknownResourceException e) {
			// Ok, the resource cannot be loaded, let's remove it!
			RegistryEntry entry = findAutoloadRegistryEntry(resourceUri);
			autoloadRegistry.getEntries().remove(entry);
			autoloadResource.save(Collections.emptyMap());
		}
	}
	
	public void unload() {
		autoloadRegistry = null;
		if (autoloadResource != null && autoloadResource.isLoaded()) {
			autoloadResource.unload();
		}
		autoloadResource = null;
		resourceSet = null;
	}


}
