package me.iamcxa.remindme.editor;

public class CommonEditorVar {

	// ��ܤ���B�ɶ���ܤ���`��
	public final int DATE_DIALOG_ID = 0;
	public final int TIME_DIALOG_ID = 1;

	public static CommonEditorVar EditorVarInstance = new CommonEditorVar();
	//���Τ���
	public DateVar TaskDate = new DateVar();
	public LocationVar TaskLocation = new LocationVar();
	public EditorFields Task = new EditorFields();
	public TaskTypeVar TaskType= new TaskTypeVar();
	public AlertVar TaskAlert= new AlertVar();
	public OtherVar TaskOther= new OtherVar();

	private CommonEditorVar(){}

	public static CommonEditorVar GetInstance(){
		return EditorVarInstance;
	}

}

//���Ȱ򥻦���
class EditorFields {
	// ����ID
	private int taskId=0;
	//���ȼ��D/�Ƶ�
	private String title ="null";
	private String content ="null";
	//���Ȩ����/�إߤ�
	private String dueDateTime ="null";
	private String dueDateString ="null";
	private String created ="null";

	//---------------Getter/Setter-----------------//
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String tittle) {
		this.title = tittle;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getDueDateString() {
		return dueDateString;
	}
	public void setDueDateString(String dueDateString) {
		this.dueDateString = dueDateString;
	}
	public String getDueDateTime() {
		return dueDateTime;
	}
	public void setDueDateTime(String dueDateTime) {
		this.dueDateTime = dueDateTime;
	}

}

//���Ȧa�I����
class LocationVar {
	//���Ȧa�I�W��/�y��
	private String locationName ="null";
	private String coordinate ="null";
	// gps�ϥήɶ�
	private int GpsUseTime = 0;
	// �g�n��
	private Double Latitude=0.0;
	private Double Longitude=0.0;
	// �O�_���j�M�L�a�I
	private Boolean isSearched = false;
	private Boolean isDropped = false;
	private Double Distance=0.0;

	//---------------Getter/Setter-----------------//
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}
	public int getGpsUseTime() {
		return GpsUseTime;
	}
	public void setGpsUseTime(int gpsUseTime) {
		GpsUseTime = gpsUseTime;
	}
	public Double getLatitude() {
		return Latitude;
	}
	public void setLatitude(Double latitude) {
		Latitude = latitude;
	}
	public Double getLongitude() {
		return Longitude;
	}
	public void setLongitude(Double longitude) {
		Longitude = longitude;
	}
	public Boolean getIsdidSearch() {
		return isSearched;
	}
	public void setIsdidSearch(Boolean isdidSearch) {
		this.isSearched = isdidSearch;
	}
	public Boolean getIsDropped() {
		return isDropped;
	}
	public void setIsDropped(Boolean isDropped) {
		this.isDropped = isDropped;
	}
	public Double getDistance() {
		return Distance;
	}
	public void setDistance(Double distance) {
		Distance = distance;
	}

}

//���ȴ�������
class AlertVar {

	//���ȬO�_����/�����ɶ�/�����g��
	private boolean isAlertOn = false;
	private String alertTime ="null";
	private String alertInterval ="null";

	//---------------Getter/Setter-----------------//
	public boolean isAlertOn() {
		return isAlertOn;
	}
	public void setAlertOn(boolean isAlertOn) {
		this.isAlertOn = isAlertOn;
	}
	public String getAlertTime() {
		return alertTime;
	}
	public void setAlertTime(String alertTime) {
		this.alertTime = alertTime;
	}
	public String getAlertInterval() {
		return alertInterval;
	}
	public void setAlertInterval(String alertInterval) {
		this.alertInterval = alertInterval;
	}
}

//�������
class DateVar {

	// ���
	private int mYear=0;
	private int mMonth=0;
	private int mDay=0;
	// �ɶ�
	private int mHour=0;
	private int mMinute=0;
	//private int target;

	//---------------Getter/Setter-----------------//
	public int getmYear() {
		return mYear;
	}
	public void setmYear(int mYear) {
		this.mYear = mYear;
	}
	public int getmMonth() {
		return mMonth;
	}
	public void setmMonth(int mMonth) {
		this.mMonth = mMonth;
	}
	public int getmDay() {
		return mDay;
	}
	public void setmDay(int mDay) {
		this.mDay = mDay;
	}
	public int getmHour() {
		return mHour;
	}
	public void setmHour(int mHour) {
		this.mHour = mHour;
	}
	public int getmMinute() {
		return mMinute;
	}
	public void setmMinute(int mMinute) {
		this.mMinute = mMinute;
	}

}

//��������/����/�u������
class TaskTypeVar{

	//�����u������
	private int priority=0;
	//���Ȥ���
	private String category="null";
	//���ȼ���
	private String tag="null";
	private String level="null";

	//---------------Getter/Setter-----------------//
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
}

// ��L����
class OtherVar{

	//�����C��
	private String collaborator="null";
	private String google_cal_sync_id="null";
	private int task_color=0;

	//---------------Getter/Setter-----------------//
	public int getTask_color() {
		return task_color;
	}
	public String getCollaborator() {
		return collaborator;
	}
	public String getGoogle_cal_sync_id() {
		return google_cal_sync_id;
	}
	public void setCollaborator(String collaborator) {
		this.collaborator = collaborator;
	}
	public void setGoogle_cal_sync_id(String google_cal_sync_id) {
		this.google_cal_sync_id = google_cal_sync_id;
	}
	public void setTask_color(int task_color) {
		this.task_color = task_color;
	}
}