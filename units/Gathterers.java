package teams.student.astraphobia.units;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;

import components.upgrade.*;
import components.weapon.Weapon;
import components.weapon.energy.*;
import components.weapon.explosive.*;
import components.weapon.kinetic.*;
import components.weapon.resource.*;
import components.weapon.utility.*;
import engine.states.Game;
import objects.GameObject;
import objects.entity.unit.Frame;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import objects.resource.Resource;
import teams.student.astraphobia.*;
import teams.student.astraphobia.units.*;

public class Gathterers extends astraphobiaUnit 
{
	ArrayList<Resource> r = new ArrayList<Resource>();
	int time = 0;
	public boolean hasSpace()
	{
		if(r.size()  < 5) 
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	public Gathterers(astraphobia p)  
	{
		super(p);

	}

	public void design()
	{

//		setFrame(Frame.MEDIUM);
		setFrame(Frame.LIGHT);
		setStyle(Style.BUBBLE);
		addWeapon(new Collector(this));
//		addWeapon(new SpeedBoost(this));
		addUpgrade(new CargoBay(this));
		//	System.out.println(this + ": " + getValue());
	}

	public void draw(Graphics g) 
	{
//		if(r.size() > 0 && isFull()) {
//			g.drawLine(getX(),getY(),getHomeBase().getX(),getHomeBase().getY());
//		}
//		else if(r.size() > 0){
//			for(Resource r1: r) {
//				if(r.get(0) == r1) {
//					g.drawLine(getX(),getY(),r1.getX(),r1.getY());
//				}
//				int index = r.indexOf(r1);
//				g.drawLine(r.get(index - 1).getX(), r.get(index - 1).getY(), r1.getX(), r1.getY());
//			}
//		}
		
		if(r.size() > 0)
		{
			if(isFull())
			{
				g.drawLine(getCenterX(), getCenterY(), getHomeBase().getCenterX(), getHomeBase().getCenterY());
			}
			else 
			{
				if(r.get(0) != null)  
				{
					g.drawLine(getCenterX(), getCenterY(), r.get(0).getCenterX(), r.get(0).getCenterY());
					if(r.size()  > 1) 
					{
						g.drawLine(r.get(0).getCenterX(), r.get(0).getCenterY(), r.get(1).getCenterX(), r.get(1).getCenterY());

						if(r.size() > 2) 
						{
							g.drawLine(r.get(1).getCenterX(), r.get(1).getCenterY(), r.get(2).getCenterX(), r.get(2).getCenterY());
							if(r.size() > 3) 
							{
								g.drawLine(r.get(2).getCenterX(), r.get(2).getCenterY(), r.get(3).getCenterX(), r.get(3).getCenterY());
								if(r.size() > 4) 
								{
									g.drawLine(r.get(3).getCenterX(), r.get(3).getCenterY(), r.get(4).getCenterX(), r.get(4).getCenterY());
									if(r.size() > 5) 
									{
										g.drawLine(r.get(4).getCenterX(), r.get(4).getCenterY(), r.get(5).getCenterX(), r.get(5).getCenterY());
										if(r.size() > 6) 
										{
											g.drawLine(r.get(5).getCenterX(), r.get(5).getCenterY(), r.get(6).getCenterX(), r.get(6).getCenterY());
										}
									}
								}
							}
						}
					}
				}
			}

		}
	}

	public void gatherNearbyResources1()
	{
		if(hasCapacity())
		{
			Resource r = getNearestResource();

			if(r != null)
			{
				moveTo(r);
			}

			collectss();
		}
	}
	
	protected void collectss()
	{
		if(!isFull() && getDistance(getNearestResource()) < Collector.MAX_RANGE * .2f)
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
	
	public void action() 
	{
		time++;
		Unit enemy = getNearestEnemy();

//		if(time % 100 == 0)
//		{
//			getWeaponTwo().use();
//		}

		if(getEnemyBase() != null) 
		{
			if(r.size() == 0)
			{
				gatherNearbyResources1();
			}
			if(getEnemiesExcludeBaseShip().size() < 5) 
			{
				gatherResources();
				returnResources();
			}
			else if(getDistance(getNearestEnemyUnit()) > 500) 
			{
				gatherResources();
				returnResources();
			}
			else if(getDistance(enemy) < 200)
			{
				turnTo(enemy);
				turnAround();
				move();
//				getWeaponTwo().use();
			}
			else 
			{
				moveTo(getHomeBase());
			}

		}
	}
	public void addResource(Resource resource) 
	{

		//		addResource(resource);
		r.add(resource);
		if(r.size() > 0) 
		{
			Sort();
		}
	}

	public void Sort() 
	{
		for (int k = 0; k < r.size(); k++) 
		{
			Resource re = r.get(k);

			if(isntGood(re)) 
			{
				r.remove(re);
				k--;
			}
		}
		boolean unsorted = false;
		while(!unsorted)
		{

			unsorted = true; 
			for (int i = 0; i < r.size() - 1; i++)
			{ 
				if(getDistance(r.get(i)) > getDistance(r.get(i+1)))
				{
					Resource temp = r.get(i);
					r.set(i, r.get(i+1));
					r.set(i + 1, temp);
					unsorted = false;
				}
			}
		}

	}
	private boolean isntGood(Resource re) 
	{
		if(re != null)
		{
			if(!(re.isInBounds()) && !(getNearestEnemy().getDistance(re) < 1500) && checkTime(re)) 
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return true;
		}
	}	

	private boolean checkTime(Resource re) 
	{
		if(getHomeBase().getCenterX() < 0) 
		{
			if(Game.getTime() < 1200 && re.getX() < 0)
			{
				return true;
			}
			else if(Game.getTime() >= 1200) 
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else 
		{
			if(Game.getTime() < 1200 && re.getX() > 0)
			{
				return true;
			}
			else if(Game.getTime() >= 1200) 
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}



	public void gatherResources()
	{
		Resource passby = getNearestResource();
		if(hasCapacity())
		{
			if(r.size() > 0) 
			{
				Resource re = null;
				if(r.get(0) != null) 
				{
					re = r.get(0);
				}

				if(re != null) 
				{
					if(re.wasPickedUp()|| isntGood(re)) 
					{
						r.remove(re);
					}
					else 
					{
						if(re != null)
						{
							moveTo(re);
						}
						collects(passby);
						collects(re);
					}
				}
			}
		}
	}

	protected void collects(Resource r)
	{
		if(!isFull() && getDistance(r) < Collector.MAX_RANGE * .5f)
		{
			if(getWeaponOne() != null && getWeaponOne() instanceof Collector)
			{
				getWeaponOne().use();
			}
//			if(getWeaponTwo() != null && getWeaponTwo() instanceof Collector)
//			{
//				getWeaponTwo().use();
//			}
		}
	}

	public void returnResources()
	{
		if(isFull())
		{
			moveTo(getHomeBase());
			deposit();
			if(getCargo() == 0) 
			{
				r.clear();
			}

		}	
	}



	public boolean hasResourceInQueue() 
	{
		if(r.size() > 0) 
		{
			return true;
		}
		else
		{
			return false;
		}
	}



	public GameObject getLastResource() 
	{
		return r.get(r.size()-1);
	}

}
