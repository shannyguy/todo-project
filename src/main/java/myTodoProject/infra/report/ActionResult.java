package myTodoProject.infra.report;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ActionResult {
	
	private String actionName;
	private String actionDbKey;
	private String status;
	private String description;
	private String errorMessage;
	private long timestamp = 0;
	private long totalTime = 0;
	private String platform = "SDK";
	
	@SerializedName("arguments") 
	private List<Parameter> parameters;
	
	private String resource;
	
	private transient String outputParameter;
	
	/**
     * Constructor
     *
     * @param actionName The name of the Action result
     */
	public ActionResult(String actionName){
		// set start time
		updateTimestamp();
		// init status
		setStatus(true);
		
		this.actionName = actionName;
		parameters = new ArrayList<Parameter>();
	}
	
	/**
	 * create a new action result
	 * @param	name			action name
	 * @param	status 			true = Success, false = Fail
	 * @param	message			the message to be displayed in the report
	 */
	public ActionResult(String name, boolean status, String message){
		this(name);
		
		setErrorMessage(message);
		setStatus(status);
	}	
	
	/**
	 * create a new action result
	 * @param	name			action name
	 * @param	status 			true = Success, false = Fail
	 */
	public ActionResult(String name, boolean status){
		this(name,status,null);
	}
	
	/**
	 * set action result status
	 * @param	status 			true = Success, false = Fail
	 */
	public void setStatus(boolean status){
		if(status)
			this.status = "Success";
		else
			this.status = "Fail";
	}
	
	/**
	 * Returns action result status as String, possible values: Success, Fail.
	 * 
	 * @return	status, values : 'Fail', 'Success'
	 */
	public String getStatus() {		
		return status;
	}
	
	/**
	 * check if the result status is 'Success'
	 * 
	 * @return	true = Success, false = Fail
	 */
	public boolean isSuccess(){
		return status.equals("Success");
	}

	/**
	 * set action result error message
	 * 
	 * @param message
	 */
	public void setErrorMessage(String message){
		this.errorMessage = message;
	}
	
	/**
	 * Returns action result error message.
	 * if there is no message, returns null
	 * 
	 * @return	error message or null if there is no message
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Set a value for the output parameter
	 * 
	 * @param outputParameter the output parameter value
	 */
	public void setOutputParameter(String outputParameter) {
		this.outputParameter = outputParameter;
	}
	
	/**
	 * Returns action result output parameter value.
	 * if there is no output value, returns null
	 * 
	 * @return	output parameter value
	 */
	public String getOutputParameter(){
		return outputParameter;
	}
	
	
	/**
	 * update the action start time to the current time. format: unix timestamp
	 * agent actions update the timestamp automatically when action is sent to the agent
	 * 
	 */
	public void updateTimestamp(){
		this.timestamp = System.currentTimeMillis();
	}
	
	/**
	 * get the action timestamp. the start time of the action. format: unix timestamp
	 * 
	 * @return	timestamp
	 */
	public long getTimestamp(){
		return timestamp;
	}
	
	/**
	 * set the action name. 
	 * 
	 * @param actionName the name of the action
	 */
	public void setActionName(String actionName){
		this.actionName = actionName;
	}
	
	/**
	 * get the action name. 
	 * 
	 * @return	action name
	 */
	public String getActionName(){
		return actionName;
	}
	
	/**
	 * get the action db key. 
	 * 
	 * @return	action db key
	 */
	public String getActionDbKey() {
		return actionDbKey;
	}

	/**
	 * set the action description. 
	 * 
	 * @param description the action description
	 */
	public void setDescription(String description){
		this.description = description;
	}
	
	/**
	 * get the action description
	 * 
	 * @return the action description
	 */
	public String getDescription(){
		return description;
	}
	
	/**
	 * Set the name of the resource that the action was performed on
     * 
     * @param resource the resource name
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}
	
	/**
	 * Get the name of the resource that the action was performed on
     * 
     * @return the resource name
	 */
	public String getResource() {
		return resource;
	}

	/**
	 * Get the total time of the action operation, in milliseconds
     * 
     * @return action total time
	 */
	public long getTotalTime() {
		return totalTime;
	}

	/**
	 * Set the total time of the action operation, in milliseconds
     * 
     * @param totalTime
	 */
	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}


	/**
	 * Get the parameters list that the action used
     * 
     * @return parameters list
	 */
	public List<Parameter> getParameters() {
		return parameters;
	}
	
	
	/**
	 * Add an input parameter for the action result report
     * 
     * @param name the name of the parameter
     * @param value the value of the parameter
	 */
	public void addParameter(String name, String value){
		if(parameters == null)
			return; 			// illegal state			
		parameters.add(new InputParameter(name, value));
	}
	
	/**
	 * Add an output parameter for the action result report
     * 
     * @param name the name of the parameter
     * @param value the value of the parameter
	 */
	public void addOutputParameter(String name, String value){
		if(parameters == null)
			return; 			// illegal state
		parameters.add(new OutputParameter(name, value));
	}
	
	@Override
	public String toString() {
		return "[Status : " + status + "] , [Action Name : " + actionName + "]" + (!isSuccess() ? " , [Error Message : " + getErrorMessage() +"]"  : "");
	}

	/**
	 * get the action platform
	 * 
	 * @return the action platform
	 */
	public String getPlatform() {
		return platform;
	}

	/**
	 * Set the action platform
     * 
     * @param platform
	 */
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	public class InputParameter extends Parameter {

		public InputParameter(String name, String value){
			super(name,value);
			this.paramtype = "INPUT";
			this.datatype = null;
		}
	}
	public class OutputParameter extends Parameter {
		
		public OutputParameter(String name, String value){
			super(name,value);
			this.paramtype = "OUTPUT";
			this.datatype = null;
		}
	}

}
