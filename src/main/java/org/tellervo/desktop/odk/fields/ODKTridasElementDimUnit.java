package org.tellervo.desktop.odk.fields;

import java.util.ArrayList;

import org.tellervo.desktop.odk.SelectableChoice;
import org.tellervo.desktop.tridasv2.doc.Documentation;
import org.tridas.interfaces.ITridas;
import org.tridas.schema.NormalTridasUnit;
import org.tridas.schema.TridasElement;

public class ODKTridasElementDimUnit extends AbstractODKChoiceField {
	
	private static final long serialVersionUID = 1L;

	
	public ODKTridasElementDimUnit()
	{
		super(ODKDataType.SELECT_ONE, "tridas_object_dimensions_unit", "Dimension units", Documentation.getDocumentation("unit"), null);
		
		ArrayList<Object> objects = new ArrayList<Object>();
		for(NormalTridasUnit type: NormalTridasUnit.values())
		{
			objects.add(type);
		}
		
		
		this.setPossibleChoices(SelectableChoice.makeObjectsSelectable(objects));
	}
	
	@Override
	public Boolean isFieldRequired() {
		return false;
	}

	@Override
	public Class<? extends ITridas> getTridasClass() {
		return TridasElement.class;
	}

}

