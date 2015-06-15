package org.dsystems.output;

import org.dsystems.utils.Attributes;
import org.dsystems.utils.ValidatorResponse;

public class OutputFactory {
	
	public static Output getOutput(Output.Type type, Attributes attrs) {
		
		Output output = null;
		switch(type) {
			case FILE: {
				output = new FileOutput();
				//output = output.init(attrs);
			}
		}
		if (output != null) {
			ValidatorResponse vr = output.init(attrs); 
			if (vr.isValid == true) {
				return output;
			} else {
				System.out.println("OutputFactory:: Error creating Output: " + vr.message);
			}
		}
		return output;
		
	}

}
