package fr.inria.atlanmod.emf.utils.service;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.osgi.util.NLS;

public class UnknownResourceException extends Exception {

	private static final long serialVersionUID = 1L;

	public UnknownResourceException(URI resourceUri) {
		this(resourceUri, null);
	}

	public UnknownResourceException(URI resourceUri, Exception e) {
		super(NLS.bind("Unknown resource {0}", resourceUri.toString()), e);
	}

}
