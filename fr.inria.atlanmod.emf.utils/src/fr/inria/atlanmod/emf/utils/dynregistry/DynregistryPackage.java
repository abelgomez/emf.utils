/**
 */
package fr.inria.atlanmod.emf.utils.dynregistry;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see fr.inria.atlanmod.emf.utils.dynregistry.DynregistryFactory
 * @model kind="package"
 * @generated
 */
public interface DynregistryPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "dynregistry";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://fr.inria.atlanmod.emf.utils/dynregistry/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "dynregistry";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DynregistryPackage eINSTANCE = fr.inria.atlanmod.emf.utils.dynregistry.impl.DynregistryPackageImpl.init();

	/**
	 * The meta object id for the '{@link fr.inria.atlanmod.emf.utils.dynregistry.impl.RegistryImpl <em>Registry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.atlanmod.emf.utils.dynregistry.impl.RegistryImpl
	 * @see fr.inria.atlanmod.emf.utils.dynregistry.impl.DynregistryPackageImpl#getRegistry()
	 * @generated
	 */
	int REGISTRY = 0;

	/**
	 * The feature id for the '<em><b>Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGISTRY__ENTRIES = 0;

	/**
	 * The number of structural features of the '<em>Registry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGISTRY_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Registry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGISTRY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link fr.inria.atlanmod.emf.utils.dynregistry.impl.RegistryEntryImpl <em>Registry Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see fr.inria.atlanmod.emf.utils.dynregistry.impl.RegistryEntryImpl
	 * @see fr.inria.atlanmod.emf.utils.dynregistry.impl.DynregistryPackageImpl#getRegistryEntry()
	 * @generated
	 */
	int REGISTRY_ENTRY = 1;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGISTRY_ENTRY__URI = 0;

	/**
	 * The number of structural features of the '<em>Registry Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGISTRY_ENTRY_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Registry Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGISTRY_ENTRY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '<em>URI</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.URI
	 * @see fr.inria.atlanmod.emf.utils.dynregistry.impl.DynregistryPackageImpl#getURI()
	 * @generated
	 */
	int URI = 2;

	/**
	 * Returns the meta object for class '{@link fr.inria.atlanmod.emf.utils.dynregistry.Registry <em>Registry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Registry</em>'.
	 * @see fr.inria.atlanmod.emf.utils.dynregistry.Registry
	 * @generated
	 */
	EClass getRegistry();

	/**
	 * Returns the meta object for the containment reference list '{@link fr.inria.atlanmod.emf.utils.dynregistry.Registry#getEntries <em>Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entries</em>'.
	 * @see fr.inria.atlanmod.emf.utils.dynregistry.Registry#getEntries()
	 * @see #getRegistry()
	 * @generated
	 */
	EReference getRegistry_Entries();

	/**
	 * Returns the meta object for class '{@link fr.inria.atlanmod.emf.utils.dynregistry.RegistryEntry <em>Registry Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Registry Entry</em>'.
	 * @see fr.inria.atlanmod.emf.utils.dynregistry.RegistryEntry
	 * @generated
	 */
	EClass getRegistryEntry();

	/**
	 * Returns the meta object for the attribute '{@link fr.inria.atlanmod.emf.utils.dynregistry.RegistryEntry#getUri <em>Uri</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Uri</em>'.
	 * @see fr.inria.atlanmod.emf.utils.dynregistry.RegistryEntry#getUri()
	 * @see #getRegistryEntry()
	 * @generated
	 */
	EAttribute getRegistryEntry_Uri();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.URI <em>URI</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>URI</em>'.
	 * @see org.eclipse.emf.common.util.URI
	 * @model instanceClass="org.eclipse.emf.common.util.URI"
	 * @generated
	 */
	EDataType getURI();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DynregistryFactory getDynregistryFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link fr.inria.atlanmod.emf.utils.dynregistry.impl.RegistryImpl <em>Registry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.atlanmod.emf.utils.dynregistry.impl.RegistryImpl
		 * @see fr.inria.atlanmod.emf.utils.dynregistry.impl.DynregistryPackageImpl#getRegistry()
		 * @generated
		 */
		EClass REGISTRY = eINSTANCE.getRegistry();

		/**
		 * The meta object literal for the '<em><b>Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REGISTRY__ENTRIES = eINSTANCE.getRegistry_Entries();

		/**
		 * The meta object literal for the '{@link fr.inria.atlanmod.emf.utils.dynregistry.impl.RegistryEntryImpl <em>Registry Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see fr.inria.atlanmod.emf.utils.dynregistry.impl.RegistryEntryImpl
		 * @see fr.inria.atlanmod.emf.utils.dynregistry.impl.DynregistryPackageImpl#getRegistryEntry()
		 * @generated
		 */
		EClass REGISTRY_ENTRY = eINSTANCE.getRegistryEntry();

		/**
		 * The meta object literal for the '<em><b>Uri</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REGISTRY_ENTRY__URI = eINSTANCE.getRegistryEntry_Uri();

		/**
		 * The meta object literal for the '<em>URI</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.URI
		 * @see fr.inria.atlanmod.emf.utils.dynregistry.impl.DynregistryPackageImpl#getURI()
		 * @generated
		 */
		EDataType URI = eINSTANCE.getURI();

	}

} //DynregistryPackage
