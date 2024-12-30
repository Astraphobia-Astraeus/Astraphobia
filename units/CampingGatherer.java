package teams.student.astraphobia.units;


import java.util.ArrayList;

import components.upgrade.*;
import components.weapon.resource.*;
import objects.entity.unit.*;
import objects.resource.*;
import teams.student.astraphobia.*;


public class CampingGatherer extends astraphobiaUnit 
{
	ArrayList<Resource> heldResources = new ArrayList<Resource>();

	public CampingGatherer(astraphobia p)  
	{
		super(p);
	}

	public void design()
	{
		setFrame(Frame.LIGHT);
//		setFrame(Frame.MEDIUM);
		setStyle(Style.BUBBLE);
		addWeapon(new Collector(this));
		addUpgrade(new CargoBay(this));
	}

	public void action() 
	{

		if(getDistance(getHomeBase()) > 250)
		{
			turnTo(getHomeBase());
			turn(90);
			move();
		}
		moveTo(getHomeBase());

		gatherNearbyResources();
		returnResources();

//		transfer();
//		dump();
	}

	public void gatherNearbyResources()
	{
		if(hasCapacity())
		{
//			for(Resource r : heldResources)
//			{
				Resource re = getNearestResource();
			
				if(re != null)
				{
					moveTo(re);
				}

				collect();
//				heldResources.remove(r);
//			}
		}
	}

	protected void collect()
	{
		if(!isFull() && getDistance(getNearestResource()) < Collector.MAX_RANGE)
		{
			if(getWeaponOne() != null && getWeaponOne() instanceof Collector)
			{
				getWeaponOne().use();
			}
			if(getWeaponTwo() != null && getWeaponTwo() instanceof Collector)
			{
				getWeaponTwo().use();
			}
		}
	}
	
	protected void collectResource(Resource r)
	{
		if(!isFull() && getDistance(r) < Collector.MAX_RANGE * .25f)
		{
			if(getWeaponOne() != null && getWeaponOne() instanceof Collector)
			{
				getWeaponOne().use();
			}
			if(getWeaponTwo() != null && getWeaponTwo() instanceof Collector)
			{
				getWeaponTwo().use();
			}
		}
	}
	
	public void addPickedUpResource(Resource resource)
	{
		heldResources.add(resource);
	}
	
	public void returnResources()
	{
//		if(getCargoPercent() > .5)
//		{
			moveTo(getHomeBase());
			deposit();
//		}	
	}


}
