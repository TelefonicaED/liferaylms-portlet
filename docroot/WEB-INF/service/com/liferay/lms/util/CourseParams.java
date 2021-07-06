package com.liferay.lms.util;

public class CourseParams {

	/**
	 * Categorías del curso
	 */
	public static final String PARAM_CATEGORIES = "categories";
	/**
	 * Tags del curso
	 */
	public static final String PARAM_TAGS = "tags";
	/**
	 * Categorías del curso
	 */
	public static final String PARAM_AND_CATEGORIES = "andCategories";
	/**
	 * Tags del curso
	 */
	public static final String PARAM_AND_TAGS = "andTags";
	/**
	 * Plantillas del curso
	 */
	public static final String PARAM_TEMPLATES = "templates";
	/**
	 * Buscar tanto en cursos padres como en cursos hijos dependiendo del parámetro courseParentId
	 */
	public static final String PARAM_SEARCH_PARENT_AND_CHILD_COURSES = "searchParentAndChildCourses";
	/**
	 * Buscar en cursos visibles en el catálogo
	 */
	public static final String PARAM_VISIBLE = "visible";
	/**
	 * Filtrar con los permisos de administrar cursos
	 */
	public static final String PARAM_PERMISSIONS_ADMIN = "permissionsAdmin";
	/**
	 * Filtrar con los permisos de ver cursos
	 */
	public static final String PARAM_PERMISSIONS_VIEW = "permissionsView";
	/**
     * Filtrar con el tipo de curso
     */
    public static final String PARAM_COURSETYPE = "courseType";
	/**
	 * Filtrar con el tipo de registro
	 */
	public static final String PARAM_TYPE = "type";
	/**
	 * Filtrar con un expando del curso
	 */
	public static final String PARAM_CUSTOM_ATTRIBUTE = "customAttribute";
	
	/**
	 * Filtrar por la fecha de inicio de ejecución del curso
	 */
	public static final String PARAM_EXECUTION_START_DATE = "executionStartDate";
	/**
	 * Filtrar por la fecha de fin de ejecución del curso
	 */
	public static final String PARAM_EXECUTION_END_DATE = "executionEndDate";
	
	public static final int STUDENTS_TYPE_ALL = 0;
	
	public static final int STUDENTS_TYPE_STARTED = 1;
	
	public static final int STUDENTS_TYPE_FINISHED = 2;
	
	public static final int STUDENTS_TYPE_PASSED = 3;
	
	public static final int STUDENTS_TYPE_FAILED = 4;
	
	public static final int  STUDENTS_GENERE_MALE = 1;
	
	public static final int  STUDENTS_GENERE_FEMALE = 0;
	
}
