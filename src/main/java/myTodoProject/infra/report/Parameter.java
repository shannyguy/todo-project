package myTodoProject.infra.report;

public class Parameter {
	
	String name;
	String value;
	String paramtype;
	String datatype;
	
	
	public Parameter(String name, String value){
		this.name = name;
		this.value = value;
		this.datatype = "STRING";   //default value for SDK
	}
	
	public String getParamtype() {
		return paramtype;
	}
	public void setParamtype(String paramtype) {
		this.paramtype = paramtype;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	

}
