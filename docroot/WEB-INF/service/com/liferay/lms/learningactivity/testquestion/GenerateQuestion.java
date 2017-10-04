package com.liferay.lms.learningactivity.testquestion;

import com.liferay.portal.kernel.exception.SystemException;

public interface GenerateQuestion {

	/**
	 * Este servicio genera el listado de preguntas en funcion del tipo de actividad de forma aleatoria.
	 * Se utiliza cuando se ha seleccionado el mecanismo de seleccion de preguntas de bancos.
	 * 
	 * @param actId
	 * @param typeId
	 * @return
	 * @throws SystemException
	 */
	public String generateAleatoryQuestions(long actId, long typeId) throws SystemException;
	
}
