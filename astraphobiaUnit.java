package teams.student.astraphobia;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;

import components.weapon.Weapon;
import components.weapon.resource.*;
import components.weapon.utility.*;
import engine.states.Game;
import objects.entity.unit.BaseShip;
import objects.entity.unit.Frame;
import objects.entity.unit.Unit;
import objects.resource.Resource;
import objects.resource.ResourceManager;
import player.Player;
import teams.student.astraphobia.units.*;

public abstract class astraphobiaUnit extends Unit 
{	
	//Kinetic (K), Energy (E), Explosive (Ex)
	public boolean usingKinetic = false;
	public boolean usingEnergy = false;
	public boolean usingExplosive = false;
	
	ArrayList<Unit> enemy = this.getEnemies();
	//Shields (S), Plating (P), Structure (St)
	public boolean usingShield = false;
	public boolean usingPlating = false;
	public boolean usingStructure = false;
	public static int amountOfLights = 0;
	public int kinetic, energy, explosive, shield, plating, structure;
	
	public float totalDPS;
	
	
	public float weapon1DMG = 0;
	public float weapon2DMG = 0;
	public float time1 = 0;
	public float time2 = 0;
	public float unitNumber = 0;
	public float tempDPS = 0;
	public float finalDPS = 0;
	public float maxHealth = 0;
	public float maxShield = 0;
	public float maxPlating = 0;
	public float maxStructure = 0;
	
	public float TTK = 0;
	
	private int checkTimer = 0;
	
	

	public astraphobiaUnit(Player p)  
	{
		super(p);	
	}
	public int middle()
	{
		int mid = 0;
		int BaseToBaseDistance = 0;
		int enemyBase = (int) getEnemyBase().getX();
		int allyBase = (int) getHomeBase().getX();
		BaseToBaseDistance = (enemyBase + allyBase) / 2;
		mid = (int) (getHomeBase().getX() + BaseToBaseDistance);
		return mid;
	}
	public void action()
	{	
		checkTimer++;
		if(checkTimer % 100 == 0)
		{
			checkWeapon();
			checkUpgrade();
		}
//		System.out.println("Average TTK: " + averageTTK(getPlayer().getOpponent()));
//		System.out.println("average enemy DPS: " + averageDPS(getPlayer().getOpponent()));
//		System.out.println("average ally DPS: " + averageDPS(getPlayer()));
	}
	
	public astraphobia getPlayer()	
	{
		return (astraphobia)(super.getPlayer());
	}
	public final Unit getLowestUnitExclude(Player p, Class<? extends Unit> clazz, int radius)
	{
		float lowestHealth = Float.MAX_VALUE;
		Unit lowestUnit = null;
		ArrayList<Unit> units =  Game.getUnits();

		for(Unit u : units)
		{
			if(this != u && u.getPlayer() == p && !clazz.isInstance(u) && u.getCurEffectiveHealth() < lowestHealth && getDistance(u) < radius * 1.5f)
			{
				lowestUnit = u;
				lowestHealth = u.getCurEffectiveHealth();
			}
		}
		return lowestUnit;
	}
	public final Unit getLowestUnitExclude2(Player p, Class<? extends Unit> clazz1, Class<? extends Unit> clazz2, int radius)
	{
		float lowestHealth = Float.MAX_VALUE;
		Unit lowestUnit = null;
		ArrayList<Unit> units =  Game.getUnits();

		for(Unit u : units)
		{
			if(this != u && u.getPlayer() == p && !clazz1.isInstance(u) && !clazz2.isInstance(u) && u.getCurEffectiveHealth() < lowestHealth && getDistance(u) < radius * 1.5f)
			{
				lowestUnit = u;
				lowestHealth = u.getCurEffectiveHealth();
			}
		}
		return lowestUnit;
	}
	
	public final Unit nearestUnitExclude2(Player p, Class<? extends Unit> clazz1, Class<? extends Unit> clazz2)
	{
		float maxDistance = Float.MAX_VALUE;
		Unit nearestExcluded = null;
		ArrayList<Unit> units =  Game.getUnits();
		
		for(Unit u : units)
		{
			if(this != u && u.getPlayer() == p && !clazz2.isInstance(u) && !clazz2.isInstance(u) && getDistance(u) < maxDistance)
			{
				maxDistance = getDistance(u);
				nearestExcluded = u;
			}
		}
		return nearestExcluded;
	}
	
	
	public float averageDPS(Player p)
	{
		ArrayList<Unit> units = Game.getUnits();
		weapon1DMG = 0;
		weapon2DMG = 0;
		time1 = 0;
		time2 = 0;
		totalDPS = 0;
		unitNumber = 0;
		tempDPS = 0;
		for(Unit u : units)
		{
			if(u.getPlayer() == p && !u.hasWeapon(MiningLaser.class) && !u.hasWeapon(Collector.class) && !(u instanceof BaseShip))
			{
				unitNumber ++;
				if(u.getWeaponOne() != null && u.getWeaponTwo() == null)
				{
					weapon1DMG = u.getWeaponOne().getDamage() * u.getWeaponOne().getNumShots();
					time1 = u.getWeaponOne().getCooldown() + u.getWeaponOne().getUseTime();
					totalDPS += weapon1DMG / time1;
					
				}
				else if(u.getWeaponOne() != null && u.getWeaponTwo() != null)
				{
					weapon1DMG = u.getWeaponOne().getDamage() * u.getWeaponOne().getNumShots();
					weapon2DMG = u.getWeaponTwo().getDamage() * u.getWeaponTwo().getNumShots();
					time1 = u.getWeaponOne().getCooldown() + u.getWeaponOne().getUseTime();
					time2 = u.getWeaponTwo().getCooldown() + u.getWeaponTwo().getUseTime();
					totalDPS += (weapon1DMG + weapon2DMG) / (time1 + time2);
				}
				else if(u.getWeaponOne() == null && u.getWeaponTwo() != null)
				{
					weapon2DMG = u.getWeaponTwo().getDamage() * u.getWeaponTwo().getNumShots();
					time2 = u.getWeaponTwo().getCooldown() + u.getWeaponTwo().getUseTime();
					totalDPS += weapon2DMG / time2;
				}
				else if(u.getWeaponOne() == null && u.getWeaponTwo() == null)
				{
					totalDPS = 6969;
				}
			}
		}
		tempDPS = totalDPS/unitNumber;
		return tempDPS;	
	}
	
	public float averageDPSInRadius(Player p, float radius)
	{
		ArrayList<Unit> units = Game.getUnits();
		 weapon1DMG = 0;
		 weapon2DMG = 0;
		 time1 = 0;
		 time2 = 0;
		totalDPS = 0;
		unitNumber = 0;
		tempDPS = 0;
		
		for(Unit u : units)
		{
			if(u.getPlayer() == p && !u.hasWeapon(MiningLaser.class) && !u.hasWeapon(Collector.class) && !(u instanceof BaseShip) 
					&& getNearestEnemyUnit().getDistance(u) <= radius)
			{
				unitNumber ++;
				if(u.getWeaponOne() != null && u.getWeaponTwo() == null)
				{
					weapon1DMG = u.getWeaponOne().getDamage() * u.getWeaponOne().getNumShots();
					time1 = u.getWeaponOne().getCooldown() + u.getWeaponOne().getUseTime();
					totalDPS += weapon1DMG / time1;
					
				}
				else if(u.getWeaponOne() != null && u.getWeaponTwo() != null)
				{
					weapon1DMG = u.getWeaponOne().getDamage() * u.getWeaponOne().getNumShots();
					weapon2DMG = u.getWeaponTwo().getDamage() * u.getWeaponTwo().getNumShots();
					time1 = u.getWeaponOne().getCooldown() + u.getWeaponOne().getUseTime();
					time2 = u.getWeaponTwo().getCooldown() + u.getWeaponTwo().getUseTime();
					totalDPS += (weapon1DMG + weapon2DMG) / (time1 + time2);
				}
				else if(u.getWeaponOne() == null && u.getWeaponTwo() != null)
				{
					weapon2DMG = u.getWeaponTwo().getDamage() * u.getWeaponTwo().getNumShots();
					time2 = u.getWeaponTwo().getCooldown() + u.getWeaponTwo().getUseTime();
					totalDPS += weapon2DMG / time2;
				}
				else if(u.getWeaponOne() == null && u.getWeaponTwo() == null)
				{
					totalDPS = 6969;
				}
			}
		}
		tempDPS = totalDPS/unitNumber;
		return tempDPS;	
	}
	

	public float averageTTK(Player p)
	{
		ArrayList<Unit> units = Game.getUnits();
		unitNumber = 0;
		TTK = 0;
		for(Unit u : units)
		{
			unitNumber ++;
			if(u.getPlayer() == p && !u.hasWeapon(MiningLaser.class) && !(u instanceof BaseShip))
			{
				if(u.getWeaponOne() != null && u.getWeaponTwo() == null && u.getWeaponOne().getDamage() 
						> 0 && u.getWeaponOne().getCooldown() > 0 && u.getWeaponOne().getUseTime() > 0)
				{
					TTK += u.getMaxEffectiveHealth() / ((u.getWeaponOne().getDamage() * u.getWeaponOne().getNumShots())/
							u.getWeaponOne().getCooldown() + u.getWeaponOne().getUseTime());
				}
				if(u.getWeaponOne() != null && u.getWeaponTwo() != null)
				{
					if(u.getWeaponOne().getDamage() > 0 && u.getWeaponOne().getCooldown() > 0 && u.getWeaponOne().getUseTime() > 0
							&& (u.getWeaponTwo().getDamage() <= 0 || u.getWeaponTwo().getCooldown() <= 0 || u.getWeaponTwo().getUseTime() <= 0))
					{
						TTK += u.getMaxEffectiveHealth() / ((u.getWeaponOne().getDamage() * u.getWeaponOne().getNumShots())/
								u.getWeaponOne().getCooldown() + u.getWeaponOne().getUseTime());
					}
					if(u.getWeaponOne().getDamage() > 0 && u.getWeaponTwo().getDamage() > 0 && u.getWeaponOne().getCooldown() > 0 && 
							u.getWeaponTwo().getCooldown() > 0 && u.getWeaponOne().getUseTime() > 0 && u.getWeaponTwo().getUseTime()> 0
							&& u.getWeaponTwo().getUseTime() > 0)
					{
						TTK += u.getMaxEffectiveHealth() / (((u.getWeaponOne().getDamage() * u.getWeaponOne().getNumShots()) + 
								(u.getWeaponTwo().getDamage() * u.getWeaponTwo().getNumShots())/ (u.getWeaponOne().getCooldown() + 
										u.getWeaponTwo().getCooldown() + u.getWeaponOne().getUseTime() + u.getWeaponTwo().getUseTime())));
					}
				}
				else if(u.getWeaponOne() == null && u.getWeaponTwo() != null)
				{
					if(u.getWeaponTwo().getDamage() > 0 && u.getWeaponTwo().getCooldown() > 0 && u.getWeaponTwo().getUseTime() > 0)
					{
					TTK += u.getMaxEffectiveHealth() / ((u.getWeaponTwo().getDamage() * u.getWeaponTwo().getNumShots())/
							u.getWeaponTwo().getCooldown() + u.getWeaponTwo().getUseTime());
					}
				}
			}
		}
		return TTK/unitNumber;
	}
	
	public float averageTTKInRadius(Player p, float radius)
	{
		ArrayList<Unit> units = Game.getUnits();
		unitNumber = 0;
		TTK = 0;
		for(Unit u : units)
		{
			unitNumber ++;
			if(u.getPlayer() == p && !u.hasWeapon(MiningLaser.class) && !(u instanceof BaseShip) &&
					getNearestEnemyUnit().getDistance(u) <= radius)
			{
				if(u.getWeaponOne() != null && u.getWeaponTwo() == null && u.getWeaponOne().getDamage() 
						> 0 && u.getWeaponOne().getCooldown() > 0 && u.getWeaponOne().getUseTime() > 0)
				{
					TTK += u.getMaxEffectiveHealth() / ((u.getWeaponOne().getDamage() * u.getWeaponOne().getNumShots())/
							u.getWeaponOne().getCooldown() + u.getWeaponOne().getUseTime());
				}
				if(u.getWeaponOne() != null && u.getWeaponTwo() != null)
				{
					if(u.getWeaponOne().getDamage() > 0 && u.getWeaponOne().getCooldown() > 0 && u.getWeaponOne().getUseTime() > 0
							&& (u.getWeaponTwo().getDamage() <= 0 || u.getWeaponTwo().getCooldown() <= 0 || u.getWeaponTwo().getUseTime() <= 0))
					{
						TTK += u.getMaxEffectiveHealth() / ((u.getWeaponOne().getDamage() * u.getWeaponOne().getNumShots())/
								u.getWeaponOne().getCooldown() + u.getWeaponOne().getUseTime());
					}
					if(u.getWeaponOne().getDamage() > 0 && u.getWeaponTwo().getDamage() > 0 && u.getWeaponOne().getCooldown() > 0 && 
							u.getWeaponTwo().getCooldown() > 0 && u.getWeaponOne().getUseTime() > 0 && u.getWeaponTwo().getUseTime()> 0
							&& u.getWeaponTwo().getUseTime() > 0)
					{
						TTK += u.getMaxEffectiveHealth() / (((u.getWeaponOne().getDamage() * u.getWeaponOne().getNumShots()) + 
								(u.getWeaponTwo().getDamage() * u.getWeaponTwo().getNumShots())/ (u.getWeaponOne().getCooldown() + 
										u.getWeaponTwo().getCooldown() + u.getWeaponOne().getUseTime() + u.getWeaponTwo().getUseTime())));
					}
				}
				else if(u.getWeaponOne() == null && u.getWeaponTwo() != null)
				{
					if(u.getWeaponTwo().getDamage() > 0 && u.getWeaponTwo().getCooldown() > 0 && u.getWeaponTwo().getUseTime() > 0)
					{
					TTK += u.getMaxEffectiveHealth() / ((u.getWeaponTwo().getDamage() * u.getWeaponTwo().getNumShots())/
							u.getWeaponTwo().getCooldown() + u.getWeaponTwo().getUseTime());
					}
				}
			}
		}
		return TTK/unitNumber;
	}
	
	public Unit piority(Player p,  Class<? extends Weapon> w1)
	{
		ArrayList<Unit> units = Game.getUnits();
		float maxDistance = Float.MAX_VALUE;
		Unit nearestPriority = null;
		for(Unit u: units) 
		{
			if(u.getPlayer() == p && u.hasWeapon(w1) && getDistance(u) < maxDistance)
			{
				maxDistance = getDistance(u);
				nearestPriority = u;
			}
		}
		return nearestPriority;
	}
	
	public Unit priorityInRadius(Player p, Class<? extends Weapon> w1, float radius)
	{
		
		ArrayList<Unit> units = Game.getUnits();
		float maxDistance = Float.MAX_VALUE;
		Unit nearestPriority = null;
		for(Unit u: units) 
		{
			if(u.getPlayer() == p && u.hasWeapon(w1) && getDistance(u) < maxDistance && getNearestEnemyUnit().getDistance(u) <= radius)
			{
				maxDistance = getDistance(u);
				nearestPriority = u;
			}
		}
		return nearestPriority;
	}


	public Point getFleetCenter()
	{
		float AvgX = 0;
		float AvgY = 0;
		int allyNum = 0;
		for(Unit u : getAlliesExcludeBaseShip())
		{
			if(u instanceof Fast || u instanceof Fighter || u instanceof Finisher || u instanceof Healer || u instanceof Comend || u instanceof Repairerer || u instanceof HeChuggin || u instanceof Sniper)
//			if(!u.hasWeapon(MiningLaser.class) || !u.hasWeapon(Collector.class) || !(u instanceof Bait))
			{
				allyNum++;
				AvgX += u.getX();
				AvgY += u.getY();
			}
		}
		AvgX = AvgX / allyNum;
		AvgY = AvgY / allyNum;
		
		return new Point(AvgX, AvgY);
	}
	
	public void onlyBaseShipLeft()
	{
		if(getDistance(getHomeBase()) > getMaxRange() + 50)
		{
			if(this.getCenterX() < getEnemyBase().getCenterX())
			{
				turnTo(getHomeBase());
				move();
			}
			else
			{
				turnTo(getEnemyBase());
				turn(90);
				move();
			}
		}
		if(getDistance(getHomeBase()) <= getMaxRange() + 50)
		{
			turnTo(getHomeBase());
			turn(90);
			move();
		}
	}
	public void checkWeapon()
	{
		kinetic = 0;
		energy = 0;
		explosive = 0;
		for(int i = 0; i < enemy.size(); i ++)
		{
		if(enemy.get(i) != getEnemyBase()) {
			if(enemy.get(i).hasKineticDamage())
			{
				kinetic ++;
			}
			if(enemy.get(i).hasEnergyDamage())
			{
				energy ++;
			}
			if(enemy.get(i).hasExplosiveDamage())
			{
				explosive ++;
			}
		}
		}
//System.out.println("Kinetic: " + kinetic + "  Energy: " + energy + "  Explosive: " + explosive);

		if(kinetic > energy && kinetic > explosive)
		{
			usingKinetic = true;			
		}
		else if(energy > kinetic && energy > explosive)
		{
			usingEnergy = true;
		}
		else if(explosive > kinetic && explosive > energy)
		{
			usingExplosive = true;
		}
		else
		{
			usingKinetic = true;	
		}
	}
	public void checkUpgrade()
	{
		shield = 0;
		plating = 0;
		structure = 0;
	
		for(int i = 0; i < enemy.size(); i ++)
		{
			if(!enemy.get(i).hasWeapon(Collector.class) || !enemy.get(i).hasWeapon(MiningLaser.class))
			{
				if(enemy.get(i).hasShield())
				{
					shield ++;
				}
				if(enemy.get(i).hasPlating())
				{
					plating ++;
				}
				if(enemy.get(i).getFrame() == Frame.LIGHT)
				{
					amountOfLights ++;
					if(enemy.get(i).getCurStructure() > 300)
					{
						structure ++;
					}
				}
				if(enemy.get(i).getFrame() == Frame.MEDIUM)
				{
					if(enemy.get(i).getCurStructure() > 450)
					{
						structure ++;
					}
				}
				if(enemy.get(i).getFrame() == Frame.HEAVY)
				{
					if(enemy.get(i).getCurStructure() > 600)
					{
						structure ++;
					}
				}
				if(enemy.get(i).getFrame() == Frame.ASSAULT)
				{
					if(enemy.get(i).getCurStructure() > 750)
					{
						structure ++;
					}
				}
			}
		}
		if(shield > plating && shield > structure)
		{
			usingShield = true;
		}
		else if(plating > shield && plating > structure)
		{
			usingPlating = true;
		}
		else if(structure > plating && structure > shield)
		{
			usingStructure = true;
		}
		else
		{
			usingPlating = true;
		}
		//System.out.println(amountOfLights);
//System.out.println("Shield: " + shield + "  Plating: " + plating + "  Structure: " + structure);
	}
	
	public void gravity() {
		Unit ally = getNearestAlly();
		if (getDistance(ally)<250) {
			turnTo(ally);
			turnAround();
			move();
		}
	}
	public static ArrayList<Resource> AllResources()
	{
		return ResourceManager.getResources();
	}
	
	public void draw(Graphics g)
	{
		for(Unit u: getEnemiesExcludeBaseShip())
		{
			if(!u.hasWeapon(MiningLaser.class) && !u.hasWeapon(Collector.class))
			{
			
				if(u.getWeaponOne() != null && u.getWeaponTwo() == null)
				{
					g.setColor(new Color(235, 56, 56, 1));
					g.fillOval(u.getCenterX() - u.getWeaponOne().getMaxRange(), u.getCenterY() - 
							u.getWeaponOne().getMaxRange(), u.getWeaponOne().getMaxRange() * 2, 
								u.getWeaponOne().getMaxRange() * 2);
					g.setColor(new Color(255,255,255));
					g.drawOval(u.getCenterX() - u.getWeaponOne().getMaxRange(), u.getCenterY() - 
							u.getWeaponOne().getMaxRange(), u.getWeaponOne().getMaxRange() * 2, 
								u.getWeaponOne().getMaxRange() * 2);
					
				}
				if(u.getWeaponOne() != null && u.getWeaponTwo() != null)
				{
					if(u.getWeaponOne().getMaxRange() > u.getWeaponTwo().getMaxRange())
					{
						g.setColor(new Color(235, 56, 56, 1));
						g.fillOval(u.getCenterX() - u.getWeaponOne().getMaxRange(), u.getCenterY() - 
								u.getWeaponOne().getMaxRange(), u.getWeaponOne().getMaxRange() * 2, 
									u.getWeaponOne().getMaxRange() * 2);
						g.setColor(new Color(255,255,255));
						g.drawOval(u.getCenterX() - u.getWeaponOne().getMaxRange(), u.getCenterY() - 
								u.getWeaponOne().getMaxRange(), u.getWeaponOne().getMaxRange() * 2, 
									u.getWeaponOne().getMaxRange() * 2);
						g.setColor(new Color(235, 203, 136, 1));
						g.fillOval(u.getCenterX() - u.getWeaponTwo().getMaxRange(), u.getCenterY() - 
								u.getWeaponTwo().getMaxRange(), u.getWeaponTwo().getMaxRange() * 2, 
									u.getWeaponTwo().getMaxRange() * 2);
						g.setColor(new Color(66, 245, 236));
						g.drawOval(u.getCenterX() - u.getWeaponTwo().getMaxRange(), u.getCenterY() - 
								u.getWeaponTwo().getMaxRange(), u.getWeaponTwo().getMaxRange() * 2, 
									u.getWeaponTwo().getMaxRange() * 2);
					}
					else
					{
						g.setColor(new Color(235, 56, 56, 1));
						g.fillOval(u.getCenterX() - u.getWeaponTwo().getMaxRange(), u.getCenterY() - 
								u.getWeaponTwo().getMaxRange(), u.getWeaponTwo().getMaxRange() * 2, 
									u.getWeaponTwo().getMaxRange() * 2);
						g.setColor(new Color(255,255,255));
						g.drawOval(u.getCenterX() - u.getWeaponTwo().getMaxRange(), u.getCenterY() - 
								u.getWeaponTwo().getMaxRange(), u.getWeaponTwo().getMaxRange() * 2, 
									u.getWeaponTwo().getMaxRange() * 2);
						g.setColor(new Color(235, 203, 136, 1));
						g.fillOval(u.getCenterX() - u.getWeaponOne().getMaxRange(), u.getCenterY() - 
								u.getWeaponOne().getMaxRange(), u.getWeaponOne().getMaxRange() * 2, 
									u.getWeaponOne().getMaxRange() * 2);
						g.setColor(new Color(66, 245, 236));
						g.drawOval(u.getCenterX() - u.getWeaponOne().getMaxRange(), u.getCenterY() - 
								u.getWeaponOne().getMaxRange(), u.getWeaponOne().getMaxRange() * 2, 
									u.getWeaponOne().getMaxRange() * 2);
					}
				}
			}

		}

	}
	
public void skirmish(Weapon w)
	{
		Unit enemy = getNearestEnemy();
		Unit ally = getNearestAlly();
		if(enemy != null)
		{
			w.use(enemy);	

			if(getDistance(enemy) > getMaxRange())
			{
				moveTo(enemy);
			}
			else
			{
				moveTo(getHomeBase());
			}
		}

	}
	
}
