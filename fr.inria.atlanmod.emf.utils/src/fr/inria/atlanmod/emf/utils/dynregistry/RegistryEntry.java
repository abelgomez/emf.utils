/**
 */
package fr.inria.atlanmod.emf.utils.dynregistry;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Registry Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link fr.inria.atlanmod.emf.utils.dynregistry.RegistryEntry#getUri <em>Uri</em>}</li>
 * </ul>
 * </p>
 *
 * @see fr.inria.atlanmod.emf.utils.dynregistry.DynregistryPackage#getRegistryEntry()
 * @model
 * @generated
 */
public interface RegistryEntry extends EObject {
	/**
	 * Returns the value of the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uri</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uri</em>' attribute.
	 * @see #setUri(URI)
	 * @see fr.inria.atlanmod.emf.utils.dynregistry.DynregistryPackage#getRegistryEntry_Uri()
	 * @model dataType="fr.inria.atlanmod.emf.utils.dynregistry.URI" required="true"
	 * @generated
	 */
	URI getUri();

	/**
	 * Sets the value of the '{@link fr.inria.atlanmod.emf.utils.dynregistry.RegistryEntry#getUri <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Uri</em>' attribute.
	 * @see #getUri()
	 * @generated
	 */
	void setUri(URI value);

} // RegistryEntry
