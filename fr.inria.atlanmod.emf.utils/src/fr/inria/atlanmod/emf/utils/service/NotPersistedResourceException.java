package fr.inria.atlanmod.emf.utils.service;

import org.eclipse.emf.common.util.URI;
import org.eclipse.osgi.util.NLS;

public class NotPersistedResourceException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotPersistedResourceException(URI resourceUri) {
		super(NLS.bind("Unable to load resource from {0}", resourceUri.toString()));
	}
}
