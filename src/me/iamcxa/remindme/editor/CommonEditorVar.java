package me.iamcxa.remindme.editor;

/**
 * @author Kent
 * @version 20140930
 */
public class CommonEditorVar {

	// 顯示日期、時間對話方塊常數
	public final int DATE_DIALOG_ID = 0;
	public final int TIME_DIALOG_ID = 1;

	public static CommonEditorVar EditorVarInstance = new CommonEditorVar();
	
	//切割分類
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

//任務基本成員
class EditorFields {
	// 任務ID
	private int taskId=0;
	//任務標題/備註
	private String title ="null";
	private String content ="null";
	//任務到期日/建立日
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

//任務地點成員
class LocationVar {
	//任務地點名稱/座標
	private String locationName ="null";
	private String coordinate ="null";
	// gps使用時間
	private int GpsUseTime = 0;
	// 經緯度
	private Double Latitude=0.0;
	private Double Longitude=0.0;
	// 是否有搜尋過地點
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

//任務提醒成員
class AlertVar {

	//任務是否提醒/提醒時間/提醒週期
	private long due_date_millis=0;
	private String due_date_string="null";
	private long interval=0;
	private int loc_id=0;
	private boolean loc_on=false;
	private int loc_radius=0;
	private String other="null";
	private int task_id=0;
	private int time_offset=0;
	private int type=0;

	//---------------Getter/Setter-----------------//
	/**
	 * @return the due_date_millis
	 */
	public long getDue_date_millis() {
		return due_date_millis;
	}
	/**
	 * @param due_date_millis the due_date_millis to set
	 */
	public void setDue_date_millis(long due_date_millis) {
		this.due_date_millis = due_date_millis;
	}
	/**
	 * @return the due_date_string
	 */
	public String getDue_date_string() {
		return due_date_string;
	}
	/**
	 * @param due_date_string the due_date_string to set
	 */
	public void setDue_date_string(String due_date_string) {
		this.due_date_string = due_date_string;
	}
	/**
	 * @return the interval
	 */
	public long getInterval() {
		return interval;
	}
	/**
	 * @param interval the interval to set
	 */
	public void setInterval(long interval) {
		this.interval = interval;
	}
	/**
	 * @return the loc_id
	 */
	public int getLoc_id() {
		return loc_id;
	}
	/**
	 * @param loc_id the loc_id to set
	 */
	public void setLoc_id(int loc_id) {
		this.loc_id = loc_id;
	}
	/**
	 * @return the loc_on
	 */
	public boolean isLoc_on() {
		return loc_on;
	}
	/**
	 * @param loc_on the loc_on to set
	 */
	public void setLoc_on(boolean loc_on) {
		this.loc_on = loc_on;
	}
	/**
	 * @return the loc_radius
	 */
	public int getLoc_radius() {
		return loc_radius;
	}
	/**
	 * @param loc_radius the loc_radius to set
	 */
	public void setLoc_radius(int loc_radius) {
		this.loc_radius = loc_radius;
	}
	/**
	 * @return the other
	 */
	public String getOther() {
		return other;
	}
	/**
	 * @param other the other to set
	 */
	public void setOther(String other) {
		this.other = other;
	}
	/**
	 * @return the task_id
	 */
	public int getTask_id() {
		return task_id;
	}
	/**
	 * @param task_id the task_id to set
	 */
	public void setTask_id(int task_id) {
		this.task_id = task_id;
	}
	/**
	 * @return the time_offset
	 */
	public int getTime_offset() {
		return time_offset;
	}
	/**
	 * @param time_offset the time_offset to set
	 */
	public void setTime_offset(int time_offset) {
		this.time_offset = time_offset;
	}
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
}

//日期成員
class DateVar {

	// 日期
	protected int mYear=0;
	protected int mMonth=0;
	protected int mDay=0;
	// 時間
	protected int mHour=0;
	protected int mMinute=0;
	//private int target;
	// 毫秒
	protected long mOnlyDateMillis=0;
	protected long mDatePulsTimeMillis=0;

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
	public long getmOnlyDateMillis() {
		return mOnlyDateMillis;
	}
	public void setmOnlyDateMillis(long mOnlyDateMillis) {
		this.mOnlyDateMillis = mOnlyDateMillis;
	}
	public long getmDatePulsTimeMillis() {
		return mDatePulsTimeMillis;
	}
	public void setmDatePulsTimeMillis(long mDatePulsTimeMillis) {
		this.mDatePulsTimeMillis = mDatePulsTimeMillis;
	}
}

//任務類型/標籤/優先成員
class TaskTypeVar{

	//任務優先等級
	private int priority=0;
	//任務分類
	private String category="null";
	//任務標籤
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

// 其他成員
class OtherVar{

	//任務顏色
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