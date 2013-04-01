package com.wzx.andapp.shh;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class HelloExpand extends ExpandableListActivity {
	public final static String NAME = "Name:";
	public final static String PHONE = "Phone:";
    public final static String SEX = "Sex:";
	
	public List<String> group;
	public List<List<String>> child;
	
	ExpandInfoAdapter adapter;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        
        initialOther();
        
        addItemByValue("Griffin","051782214","man");
        addItemByValue("Billy","110","mal");
        addItemByValue("Kindy","132","femal");
        addItemByValue("Patric","13321234562","femal");
        
        adapter = new ExpandInfoAdapter(this);
        
        this.setListAdapter(adapter);
        
    }
    
    public void initialOther(){
    	group = new ArrayList<String>();
    	child = new ArrayList<List<String>>();
    	
    }
    
    public void addItemByValue(String n,String c1,String c2){
    	group.add(n);
    	
    	List<String> item = new ArrayList<String>();
    	
    	item.add(NAME+n);
    	item.add(PHONE+c1);
    	item.add(SEX+c2);
    	
    	child.add(item);
    }
    
    public class ExpandInfoAdapter extends BaseExpandableListAdapter {
    	Activity activity;
    	
    	public ExpandInfoAdapter(Activity a){
    		activity = a;
    		
    	}
    	
    	
    	//++++++++++++++++++++++++++++++++++++++++++++
    	// child's stub
    	
		@Override
		public Object getChild(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return child.get(arg0).get(arg1);
		}

		@Override
		public long getChildId(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return arg1;
		}
		
		@Override
		public int getChildrenCount(int arg0) {
			// TODO Auto-generated method stub
			return child.get(arg0).size();
		}

		@Override
		public View getChildView(int arg0, int arg1, boolean arg2, View arg3,
				ViewGroup arg4) {
			// TODO Auto-generated method stub
			return getChildViewStub(child.get(arg0).get(arg1).toString());
		}
		
		public TextView getChildViewStub(String s) {
            // Layout parameters for the ExpandableListView
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, 64);

            TextView text = new TextView(activity);
            text.setLayoutParams(lp);
            text.setTextSize(20);
            text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            text.setPadding(36, 0, 0, 0);
            text.setText(s);
            return text;
        }
		
		
		//++++++++++++++++++++++++++++++++++++++++++++
    	// group's stub
		
		@Override
		public Object getGroup(int arg0) {
			// TODO Auto-generated method stub
			return group.get(arg0);
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return group.size();
		}

		@Override
		public long getGroupId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getGroupView(int arg0, boolean arg1, View arg2,
				ViewGroup arg3) {
			// TODO Auto-generated method stub
			return getGroupViewStub(getGroup(arg0).toString());
		}

		public TextView getGroupViewStub(String s) {
            // Layout parameters for the ExpandableListView
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, 64);

            TextView text = new TextView(activity);
            text.setLayoutParams(lp);
            text.setTextSize(20);
            text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            text.setPadding(36, 0, 0, 0);
            text.setText(s);
            return text;
        }
		
		// Indicate whether Group is Expanded or Collapsed
		public void onGroupExpanded(int groupPosition){
        }
        
        public void onGroupCollapsed(int groupPosition){
        }
		
        @Override
		public boolean isChildSelectable(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return true;
		}
		
		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

    	
    }
}