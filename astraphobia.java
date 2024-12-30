package teams.student.astraphobia;

import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import components.weapon.resource.Collector;
import components.weapon.resource.MiningLaser;
import engine.states.Game;
import objects.entity.missile.Missile;
import objects.entity.unit.Frame;
import objects.entity.unit.Unit;
import objects.resource.Resource;
import player.Player;
import teams.student.astraphobia.units.*;

public class astraphobia extends Player
{	

	public static boolean cheese = false;
	public static boolean fastCheese = false;
	private int timer = 0;
	private int randomTimer = 0;
	public int lights;
	
	
	private int resourceTimer = 0;
	private int resourceTimer2 = 0;
	private int colorTimer = 0;
	
	public int r, g, b, rTimer, gTimer, bTimer;
	
	
	ArrayList<Resource> safeResourceList  = new ArrayList <Resource>();
	ArrayList<Resource> newResources = new ArrayList <Resource>();
	ArrayList<Resource> taggedResource = new ArrayList <Resource>();
	
	ArrayList<Resource> pickedUpResources = new ArrayList<Resource>();
	ArrayList<Resource> totalResources = new ArrayList<Resource>();
	
	ArrayList<Gathterers> gathererList = new ArrayList <Gathterers>();
	ArrayList<Gathterers> newGatherers = new ArrayList <Gathterers>();	
	ArrayList<Unit> enemyUnits = new ArrayList <Unit>();	
	
	boolean increasing = true;
	int step = 1;
	int direction = 1;
		
	public void setup()
	{
		setName("Astraphobia");
		setTeamImage("src/teams/student/astraphobia/Astraphobia.png");
		setTitle("storm sucks");
		
	}
	public void opening()
	{ 
		timer = 0;
		colorTimer = 0;
		resourceTimer = 0;
		taggedResource.clear();
		gathererList.clear();
		newGatherers.clear();
		newResources.clear();
		safeResourceList.clear();
		enemyUnits.clear();
		pickedUpResources.clear();
		totalResources.clear();
//		buildUnit(new Bait(this));
//		buildUnit(new CampingGatherer(this));
//		buildUnit(new CampingGatherer(this));
		buildUnit(new Gathterers(this));
		buildUnit(new Gathterers(this));
		buildUnit(new Gathterers(this));
		buildUnit(new Grub(this));
		buildUnit(new Grub(this));
		buildUnit(new Grub(this));

		r = 0;
		g = 255;
		b = 0;

		setColorPrimary(r, g, b);
		setColorSecondary(r, g, b);
		setColorAccent(255,255,255);
	}

	public void strategy() 
	{	
		
//		colorTimer++;
//		if(colorTimer % 5 == 0)
//		{	
//			if(direction == 1) 
//			{
//				if(r < 255) 
//				{
//					r += step;
//				} 
//				else 
//				{
//					direction = 2;
//				}
//			} 
//			else if(direction == 2) 
//			{
//				if(g < 255) 
//				{
//					g += step;
//				} 
//				else 
//				{
//					direction = 3;
//				}
//			} 
//			else if(direction == 3) 
//			{
//				if(b < 255) 
//				{
//					b += step;
//				} 
//				else 
//				{
//					direction = 4;
//				}
//			} 
//			else if(direction == 4) 
//			{
//				if(r > 75) 
//				{
//					r -= step;
//				} 
//				else 
//				{
//					direction = 5;
//				}
//			}
//			else if(direction == 5) 
//			{
//				if(g > 75) 
//				{
//					g -= step;
//				} 
//				else 
//				{
//					direction = 6;
//				}
//			} 
//			else if(direction == 6) 
//			{
//				if(b > 75) 
//				{
//					b -= step;
//				} 
//				else
//				{
//					direction = 1;
//				}
//			} 
//			else 
//			{
//					direction = 1; //reset direction to start the cycle again
//			}
//		
//		}
		
//		setColorPrimary(r, g, b);
//		setColorSecondary(r, g, b);
//		setColorAccent(255,255,255);
		 /***** RESOURCE STUFF *****/
		
		resourceTimer++;
		

		
		if(resourceTimer % 90 == 0)
		{
			newGatherers.addAll(getAllGathterers());

			sortNewGatherers();
			
			enemyUnits.clear();
			enemyUnits.addAll(getEnemyUnits());
		}
		
		if(resourceTimer % 30 == 0) 
		{
			safeResourceList.clear();
			getNewResources();		
			sortNewResources();
			checkResources();
			getResource();
			
			
//			for(Gathterers g : gathererList)
//			{
//				for(Resource re : g.getResources())
//				{
//					if(!taggedResource.contains(re))
//					{
//						taggedResource.add(re);
//					}
//				}
//			}
			
		}

		if(resourceTimer % 150 == 0) 
		{
			if(newGatherers != null) 
			{
				newGatherers.clear();
			}
		}
		
		if(resourceTimer % 2000 == 0)
		{
			taggedResource.clear();
		}
		
		/***** STRATEGY STUFF *****/
		basicStrategy();
//		strat();
		
	}
	
	private void strat()
	{
		if(getFleetValue(HeChuggin.class) >= 1.5f)
		{
			cheese = true;
		}
		else
		{
			cheese =  false;
		}
		if(getFleetValue(Fast.class) >= 1f)
		{
			fastCheese = true;
		}
		else
		{
			fastCheese = false;
		}
		if(timer >= 500 && timer <= 1000 && (countEnemyUnits() > 10))
		{
			if(astraphobiaUnit.amountOfLights <= 1)
			{
				littleLights();
			}
			else
			{
				basicStrategy();
			}
		}
		else if(timer > 1000 && countEnemyUnits() < 10 && countEnemyUnits() > 1)
		{
			if(astraphobiaUnit.amountOfLights <= 1)
			{
				littleLights();
			}
			else
			{
				basicStrategy();
			}
		}
		else if((timer > 1000) && (countEnemyUnits() > 10))
		{
			if(astraphobiaUnit.amountOfLights <= 1)
			{
				littleLights();
			}
			else
			{
				basicStrategy();
			}
		}
		else
		{
			basicStrategy();
		}
 
		if(timer > 1000 && countEnemyUnits() <= 1)
		{
			onlyBase();
		}
	}
			
	public void draw(Graphics g) 
	{
		addMessage("Total Resources: " + totalResources.size());
		addMessage("Picked Up Resources: " + pickedUpResources.size());
		addMessage("Tagged Resources: " + taggedResource.size());
		addMessage("Safe Resource: " + safeResourceList.size());
		addMessage("");
		
		g.setColor(Color.red);
		for(Resource r : taggedResource)
		{
			if(r != null)
			{
				g.drawRect(r.getCenterX() - 15, r.getCenterY() - 15, 30, 30);
			}
		}

	}
	
	public void littleLights()
	{
		if(getFleetValuePercentage(Gathterers.class) <= .13f)
		{
			buildUnit(new Gathterers(this));
		}
		else if(getFleetValue(Grub.class) < 6)
		{
			buildUnit(new Grub(this));
		}
		else if(getFleetValue(Comend.class) < 2)
		{
			buildUnit(new Comend(this));
		}
		else 
		{
			buildUnit(new HeChuggin(this));
		}
	}
	public void onlyFast()
	{
		
		if(getFleetValue(Gathterers.class) <= 1)
		{
			buildUnit(new Gathterers(this));
		}
		else if(getFleetValue(Fast.class) < 1)
		{
			buildUnit(new Fast(this));
		}
		else
		{
			buildUnit(new Fast(this));
		}
	}
	public void onlyBase()
	{
		if((getFleetValuePercentage(Gathterers.class) <= .15f))
		{
			buildUnit(new Gathterers(this));
		}
		else if((getFleetValuePercentage(Grub.class) <= .15f))
		{
			buildUnit(new Grub(this));
		}
		else
		{
			buildUnit(new HeChuggin(this));
		}
	}
	public void basicStrategy()
	{
		if((getFleetValuePercentage(Gathterers.class) < .18f))
		{
			buildUnit(new Gathterers(this));
		}
		else if((getFleetValuePercentage(Grub.class) < .12f))
		{
			buildUnit(new Grub(this));
		}
		else if(getFleetValuePercentage(Fast.class) <= .25f)
		{
			buildUnit(new Fast(this));
		}
		else if(getFleetValuePercentage(Comend.class) < .02f)
		{
			buildUnit(new Comend(this));
		}
		else 
		{
			buildUnit(new HeChuggin(this));
		}	
	}
//	public void sortFrame() 
//	{
//		int[] values = {l, m, h, a};
//		Arrays.sort(values);
//		first = values[3];
//		second = values[2];
//		third = values[1];
//		fourth = values[0];
//
//	}
	
	
	//see if resources has new resource
	private void sortNewResources() 
	{
		if(newResources != null) 
		{
			for(int i = 0; i < newResources.size(); i++) 
			{
				if(safeResourceList.contains(newResources.get(i)))
				{
					newResources.remove(i);
					if(i > 0) 
					{i--;}
				}
				else if(!newResources.get(i).isInBounds()) 
				{
					newResources.remove(i);
					if(i > 0) 
					{i--;}
				}

				if(getMyBase().getCenterX() < 0) 
				{
					if(newResources.get(i).getX() < 0 + getMyBase().getX()+ 7000)
					{
						safeResourceList.add(newResources.get(i));
					}
					else
					{
						newResources.remove(i);
						if(i > 0) 
						{i--;}
					}
				}
				else 
				{
					if(newResources.get(i).getX() > 0)
					{
						safeResourceList.add(newResources.get(i));
					}
					else
					{
						newResources.remove(i);
						if(i > 0) 
						{i--;}
					}
				}
			}
		}
	}

	public boolean isTagged(Resource r) 
	{
		if(taggedResource.contains(r)) 
		{
			safeResourceList.remove(r);
			return false;
		}
		else
		{
			taggedResource.add(r);
			return true;
		}	

	}

	//returns all current gatherers
	public ArrayList<Gathterers> getAllGathterers()
	{
		ArrayList<Gathterers> gatherers = new ArrayList<Gathterers>();
		ArrayList<Unit> myUnits = getMyUnits();	
		
		for(Unit u : myUnits)
		{
			if(u instanceof Gathterers)
			{
				gatherers.add((Gathterers)u);
			}
		}
		return gatherers;
	}
	
	private void sortNewGatherers() 
	{
		if(newGatherers != null) 
		{
			for(int i = 0; i < newGatherers.size(); i++) 
			{
				if(gathererList.contains(newGatherers.get(i)))
				{
					newGatherers.remove(i);
					i--;
				}
				else 
				{
					gathererList.add(newGatherers.get(i));
				}
			}
		}
	}

	//get all Resources
	private void getNewResources() 
	{
		totalResources.clear();
		if(newResources != null) 
		{
			if(getAllResources() != null) 
			{
				newResources.addAll(getAllResources());
				totalResources.addAll(getAllResources());
			}
		}

	}
	//check and see if a resource is safe, in bounds and not tagged
	//safe part hasn't been added 
	private void checkResources() 
	{
//		for(int i = 0; i < safeResourceList.size() ; i++) 
//		{
//			Resource r = safeResourceList.get(i);
//				
//			if(r.wasPickedUp() || !r.isInBounds())
//			{
//				taggedResource.remove(r);
//				removeTagged(r);
//				safeResourceList.remove(r);
//				i--;
//			}
//		}
		int xfgbhlnkj = 0;
		for(int i = 0; i < taggedResource.size(); i++)
		{
			Resource r = taggedResource.get(i);
			if(r != null)
			{
				if(r.isInBounds())
				{
					
				}
				else
				{
					taggedResource.remove(r);
					i--;
				}
			}
			else
			{
				taggedResource.remove(r);
				i--;
			}
			if(r != null)
			{
				if(!r.isMoving())
				{
					xfgbhlnkj++;
					if(xfgbhlnkj > 100)
					{
						taggedResource.remove(r);
						xfgbhlnkj = 0;
						i--;
					}
				}
			}

		}
		
		for(int i = 0; i < safeResourceList.size(); i++) 
		{
			Resource re = safeResourceList.get(i);
			if(re != null)
			{
				if(re.isInBounds()) 
				{

				}
				else
				{
//					taggedResource.remove(re);
					safeResourceList.remove(re);
//					removeTagged(re);
					i--;
				}
			}
			else
			{
//				taggedResource.remove(re);
				safeResourceList.remove(re);
//				removeTagged(re);
				i--;
			}
		}
		
		
	}	

	public void getResource() 
	{
		if(gathererList != null)
		{
			for(int i = 0; i < gathererList.size() ; i++) 
			{
				Gathterers gb = gathererList.get(i);
				if(gb.hasSpace()) 
				{
					gb.addResource(getBestResource(gb));
				}
				if(gb.hasSpace()) 
				{
					gb.addResource(getBestResource(gb));
				}
				if(gb.hasSpace()) 
				{
					gb.addResource(getBestResource(gb));
				}
				if(gb.hasSpace()) 
				{
					gb.addResource(getBestResource(gb));
				}
				if(gb.hasSpace()) 
				{
					gb.addResource(getBestResource(gb));
				}
				
			
				
			}
		}
	}





	private Resource getBestResource(Gathterers g) 
	{
		
		float nearestDistance = Float.MAX_VALUE;
		Resource nearestResource = null;

		for(int i = 0; i < safeResourceList.size();i++)
		{
			float distance;
			Resource r = safeResourceList.get(i);
				
			if(taggedResource.contains(r)) 
			{
				safeResourceList.remove(r);
				i--;
			}
			
			/***** FOR THROWING *****/
			
//			else if(pickedUpResources.contains(r))
//			{
//				safeResourceList.remove(r);
//				i--;
//			}
			
			/************************/

			
			else
			{
				//no resources, compare to current location
				if(!g.hasResourceInQueue()) 
				{
					distance = r.getDistance(g);
				}

				//has a queue, so we look at last resource in queue
				else 
				{
					distance = r.getDistance(g.getLastResource());
				}

				if(distance < nearestDistance) 
				{
					nearestDistance = distance;
					nearestResource = r;
				}
			}
		}
			taggedResource.add(nearestResource);
			return nearestResource;
	}
		
	public boolean removeTagged(Resource r) 
	{
		if(taggedResource.contains(r) && safeResourceList.contains(r)) 
		{
			taggedResource.remove(r);
			safeResourceList.remove(r);
			return false;
		}
		else
		{
			
			return true;
		}
	}
	
}

