package io.github.abelgomez.emf.utils.ui.tester;

import java.util.Collections;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

public class EcoreResourcePropertyTester extends PropertyTester {

	private static final String IS_ECORE_RESOURCE= "isEcoreResource";  
	
	public EcoreResourcePropertyTester() {
	}

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (IS_ECORE_RESOURCE.equals(property) && receiver instanceof IFile) {
			boolean expected = (Boolean) expectedValue;
			IFile file = (IFile) receiver;
			ResourceSet resourceSet = new ResourceSetImpl();
			Resource resource = null;
			try {
				resource = resourceSet.getResource(URI.createPlatformResourceURI(file.getFullPath().toString(), true), true);
				resource.load(Collections.emptyMap());
				if ((resource.getContents().get(0) instanceof EPackage) == expected) {
					return true;
				}
			} catch (Throwable t) {
				// Unable to load file as an Ecore resource, do nothing
			} finally {
				if (resource != null && resource.isLoaded()) {
					resource.unload();
				}
			}
		}
		return false;
	}

}
