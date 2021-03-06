/**
 */
package io.github.abelgomez.emf.utils.dynregistry;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Registry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link io.github.abelgomez.emf.utils.dynregistry.Registry#getEntries <em>Entries</em>}</li>
 * </ul>
 *
 * @see io.github.abelgomez.emf.utils.dynregistry.DynregistryPackage#getRegistry()
 * @model
 * @generated
 */
public interface Registry extends EObject {
	/**
	 * Returns the value of the '<em><b>Entries</b></em>' containment reference list.
	 * The list contents are of type {@link io.github.abelgomez.emf.utils.dynregistry.RegistryEntry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entries</em>' containment reference list.
	 * @see io.github.abelgomez.emf.utils.dynregistry.DynregistryPackage#getRegistry_Entries()
	 * @model containment="true"
	 * @generated
	 */
	EList<RegistryEntry> getEntries();

} // Registry
