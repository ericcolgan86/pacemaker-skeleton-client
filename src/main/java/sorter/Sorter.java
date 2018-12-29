package sorter;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import models.Activity;
public class Sorter {

	public Sorter() {
	}

	public List<Activity> sortActivities(Collection<Activity> activities) {
		List<Activity> activitiesList = new ArrayList<>();
		activitiesList = (List<Activity>) activities;	     
		activitiesList.sort((Activity a1, Activity a2) -> a1.getType().compareTo(a2.getType()));
		return activitiesList;
	}

	public List<Activity> sortActivitiesByType(Collection<Activity> activities, String type) {
		List<Activity> activitiesList = (List<Activity>) activities;
		List<Activity> filteredList = new ArrayList<Activity>();
		
		activitiesList.forEach(a -> {
			if (a.type.equals(type))
				filteredList.add(a);
		});
		
		filteredList.sort((Activity a1, Activity a2) -> a1.getDistance().compareTo(a2.getDistance()));
		List<Activity> descending = Lists.reverse(filteredList);
		return descending;
	}

}