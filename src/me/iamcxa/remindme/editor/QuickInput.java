package me.iamcxa.remindme.editor;

public class QuickInput {
	/*
	 * 0��null
	 * �q1�}�l �ɶ� �a�I �Ʊ�
	 */
	static String[] StringArray =new String[10];
	public static String[] QuickInput(String OriginalString){
		StringArray= OriginalString.split(" ");
		StringBuffer Order = new StringBuffer();
		Order.append(StringArray[0]);
		String string = Order.reverse().toString();
		String[] returnString = new String[10];
		for(int a=0;StringArray[0].length()>a;a++)
		{
			System.out.println(Integer.parseInt(string)%10);
			switch ((Integer.parseInt(string)%10)) {
			case 1:
				returnString[1]=StringArray[a+1];
				break;
			case 2:
				returnString[2]=StringArray[a+1];
				break;
			case 3:
				returnString[3]=StringArray[a+1];
				break;
			case 4:
				returnString[4]=StringArray[a+1];
				break;
	
			default:
				break;
			}
			
			string = string.substring(0, string.length()-1);
			//System.out.println(a+","+string.substring(0, string.length()-1));
		}
		return returnString;
	}

	
	public static String[] TimeQuickInput(String time){
		String[] TimeArray =new String[10];
		time = time.replaceAll("\\. ", ".0");
		TimeArray =time.split("\\.");
		return TimeArray;
	}
	
}
