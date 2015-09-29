package io.github.abelgomez.emf.utils.service;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;

import io.github.abelgomez.emf.utils.dynregistry.RegistryEntry;

public interface RegistryManager {

	/**
	 * Returns the registered {@link EPackage} corresponding to the
	 * given nsUri
	 * 
	 * @param nsUri
	 *            The {@link EPackage} {@link URI}
	 * @return The {@link EPackage}
	 * @throws UnknownEPackageException
	 *             No {@link EPackage} is registered with the given
	 *             {@link URI}
	 */
	public EPackage getEPackage(URI nsUri) throws UnknownEPackageException;

	/**
	 * Loads and returns the {@link EPackage} contained in the resource pointed
	 * by {@link URI}
	 * 
	 * @param resourceUri
	 *            The {@link Resource} {@link URI}
	 * @return The {@link EPackage}
	 * @throws IOException
	 *             The {@link Resource} cannot be loaded
	 * @throws UnknownResourceException
	 *             If the URI does not point to a valid {@link Resource}
	 */
	public EPackage loadEPackage(URI resourceUri) throws UnknownResourceException, IOException;

	/**
	 * Adds an {@link EPackage} to the EMF {@link EPackage}s
	 * {@link org.eclipse.emf.ecore.EPackage.Registry}
	 * 
	 * The {@link EPackage} will remain registered until the next platform
	 * restart
	 * 
	 * @param ePackage
	 *            The {@link EPackage}
	 */
	public void registerEPackage(EPackage ePackage);

	/**
	 * Adds an {@link EPackage} to the EMF {@link EPackage}s
	 * {@link org.eclipse.emf.ecore.EPackage.Registry}
	 * 
	 * If <code>autoload</code> is set to <code>true</code> and the ePackage has
	 * been loaded from a persisted {@link Resource} (loaded from an *.ecore
	 * file), the {@link Resource} will be marked to be automatically loaded on
	 * future executions of the platform
	 * 
	 * @param ePackage
	 *            The {@link EPackage}
	 * @param autoload
	 *            Load the {@link EPackage} from its {@link Resource}
	 *            automatically on future executions
	 * @throws NotPersistedResourceException
	 *             The provided {@link EPackage} is not contained in a persisted
	 *             resource
	 * @throws IOException
	 *             Unable to update the "autoload" registry
	 */
	public void registerEPackage(EPackage ePackage, boolean autoload) throws NotPersistedResourceException, IOException;

	/**
	 * Removes the given {@link EPackage} from the EMF {@link EPackage}s
	 * {@link org.eclipse.emf.ecore.EPackage.Registry}. If the {@link EPackage}
	 * is marked as "autoloaded" it is also removed from the "autoload" registry
	 * 
	 * @param ePackage
	 *            The {@link EPackage}
	 * @throws IOException
	 *             Unable to update the "autoload" registry
	 */
	public void unregisterEPackage(EPackage ePackage) throws IOException;


	/**
	 * Returns a read-only list of the "autoload" registry entries
	 * 
	 * @return The "autoload" {@link RegistryEntry}s
	 */
	public List<RegistryEntry> getAutoloadEntries();
	
	/**
	 * Removes a dangling {@link Resource} from the "autoload" registry
	 * 
	 * @throws IOException
	 *             Unable to update the "autoload" registry
	 * @throws NotDanglingResourceException
	 *             The {@link Resource} exists in the filesystem and is not
	 *             dangling. The unregisterEPackage method must be used instead.
	 */
	public void removeDanglingAutoloadEntry(URI resourceUri) throws IOException, NotDanglingResourceException;
	

}
