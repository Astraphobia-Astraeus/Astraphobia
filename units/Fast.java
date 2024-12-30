package teams.student.astraphobia.units;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import components.upgrade.Plating;
import components.upgrade.Shield;
import components.upgrade.Structure;
import components.weapon.Weapon;
import components.weapon.energy.EnergySiphon;
import components.weapon.energy.SmallLaser;

import components.weapon.kinetic.MachineGun;

import components.weapon.resource.Collector;
import components.weapon.resource.MiningLaser;

import objects.entity.unit.BaseShip;
import objects.entity.unit.Frame;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.astraphobia.*;

public class Fast extends astraphobiaUnit 
{
	public boolean s1 = false;
	public boolean p1 = false;
	public boolean st1 = false;
	public boolean s_e = false;
	public boolean m_m = false;
	int turnDir;

	public int enemyX = 0;
	public int enemyY = 0;


	float rand=(float) Math.random();


	public Fast(astraphobia p)  
	{
		super(p);
	}

	public final Unit TouchMe()
	{
		return getNearestAlly(Fast.class);
	}

	public void design()
	{
		setFrame(Frame.LIGHT);
		setStyle(Style.DAGGER);
//		checkWeapon();
//		checkUpgrade();

		if(usingKinetic)
		{
			s1 = true;
			p1 = false;
			st1 = false;
			addUpgrade(new Shield(this));
		}
		else if(usingEnergy)
		{		
			p1 = true;
			s1 = false;
			st1 = false;
			addUpgrade(new Plating(this));
		}
		else
		{
			st1 = true;
			p1 = false; 
			s1 = false;
			addUpgrade(new Shield(this));
		}
		if(usingShield)
		{
			s_e = true;
			m_m = false;
			addWeapon(new SmallLaser(this));

		}
		else if(usingPlating)
		{
			m_m = true;
			s_e = false;
			addWeapon(new MachineGun(this));
		}
		else
		{
			addWeapon(new MachineGun(this));
		}
	}


	public void action() 
	{


		//skirmish(getWeaponOne());
		movement();
		attack(getWeaponOne());

	}	


	//	public final Unit getLowestUnitExclude(Player p, Class<? extends Unit> clazz, int radius)
	//	{
	//		float lowestHealth = Float.MAX_VALUE;
	//		Unit lowestUnit = null;
	//		ArrayList<Unit> units =  Game.getUnits();
	//
	//		for(Unit u : units)
	//		{
	//			if(this != u && u.getPlayer() == p && !clazz.isInstance(u) && u.getCurEffectiveHealth() < lowestHealth && getDistance(u) < radius * 1.5f)
	//			{
	//				lowestUnit = u;
	//				lowestHealth = u.getCurEffectiveHealth();
	//			}
	//		}
	//		return lowestUnit;
	//	}

	public void attack(Weapon w) {
		if(w == null)
		{
			return;
		}
		
		Unit enemy = getLowestUnitExclude(getPlayer().getOpponent(), BaseShip.class, getMaxRange()+50);
		ArrayList<Unit> enemys = getEnemies();

		
		int enemiesInRadius = countEnemiesInRadius(getMaxRange() + 150);
		int alliesInRadius = countAlliesInRadius(getMaxRange() + 300);
		Unit ally = getNearestAlly();
		ArrayList<Unit> allAllies = getAllies();

		if(enemy == null)
		{
			enemy = getNearestEnemy();
		}
		
//		if(enemiesInRadius >= alliesInRadius + 2)
//		{
//			for(int i = 0; i < allAllies.size(); i ++)
//			{
//				if(!allAllies.get(i).hasWeapon(Collector.class) || !allAllies.get(i).hasWeapon(MiningLaser.class) && (allAllies.get(i).getDistance(allAllies.get(i).getCenterX(), allAllies.get(i).getCenterY()) > 600))
//				{
//					moveTo(allAllies.get(i));
//				}
//			}
//
//			//moveTo(getHomeBase());
//		}
		
		
		else if(enemy != null)
		{
			if((getDistance(getEnemyBase())) < (getEnemyBase().getMaxRange() * 1.3f))
			{
				w.use(enemy);
			}
			w.use(enemy);	
		}
	}

	
	public void movement() 
	{		
		Unit enemy = getLowestUnitExclude(getPlayer().getOpponent(), BaseShip.class, getMaxRange()+50);
		ArrayList<Unit> enemys = getEnemies();
		
//		for(Unit u : getAlliesExcludeBaseShip())
//		{
//			if(u instanceof Fast || u instanceof Comend || u instanceof HeChuggin)
//			{
				if(/*u.*/getDistance(getFleetCenter()) > 500)
				{
					/*u.*/moveTo(getFleetCenter());
				}
//			}
//		}
		
		if((getDistance(getEnemyBase())) < (getEnemyBase().getMaxRange() * 1.3f))
		{
			turnTo(getEnemyBase());
			turn(90 * turnDir);
			move();
		}
		
		if (rand>.5) 
		{
			turnDir=3;
		} else {
			turnDir=1;
		}

		if(enemys.size() == 1)
		{			
			moveTo(getHomeBase());
			if(getDistance(getHomeBase()) < getMaxRange()*3)
			{
				turnTo(getHomeBase());
				turn(90*turnDir);
				move();
			}
		}
		
		int enemiesInRadius = countEnemiesInRadius(getMaxRange() + 150);
		int alliesInRadius = countAlliesInRadius(getMaxRange() + 300);
		Unit ally = getNearestAlly();
		ArrayList<Unit> allAllies = getAllies();

		float pOrbitr = 0;  

		if(usingPlating)
		{
			pOrbitr = .85f;
		}
		if(usingExplosive)
		{
			pOrbitr = .7f;
		}

		if(enemy == null)
		{
			enemy = getNearestEnemy();
		}
		
//		if(enemiesInRadius >= alliesInRadius + 2)
//		{
//			for(int i = 0; i < allAllies.size(); i ++)
//			{
//				if(!allAllies.get(i).hasWeapon(Collector.class) || !allAllies.get(i).hasWeapon(MiningLaser.class) && (allAllies.get(i).getDistance(allAllies.get(i).getCenterX(), allAllies.get(i).getCenterY()) > 600))
//				{
//					moveTo(allAllies.get(i));
//				}
//			}
//
//			//moveTo(getHomeBase());
//		}
		
		
		if(countAlliesInRadius(getMaxRange()*3) <= 4 && enemiesInRadius<=4) 
		{
			turnTo(enemy);
			turnAround();
			for(int i = 0; i < allAllies.size(); i ++)
				{
					if(!allAllies.get(i).hasWeapon(Collector.class) || !allAllies.get(i).hasWeapon(MiningLaser.class) &&
					((allAllies.get(i).getDistance(allAllies.get(i).getCenterX(),allAllies.get(i).getCenterY()))> getMaxRange() * 3) )
					{
						moveTo(allAllies.get(i));
					}
				}
		}
		else if(enemy != null)
		{

			if((getDistance(enemy) > getMaxRange()) && enemy.isInBounds())
			{
				enemyX = (int) enemy.getCenterX();
				enemyY = (int) enemy.getCenterY();

				moveTo(enemy);
			}
			else if(getDistance(enemy) < getMaxRange() * pOrbitr && enemy.isInBounds())
			{ 
				enemyX = (int) enemy.getCenterX();
				enemyY = (int) enemy.getCenterY();
				turnTo(enemy);
				turn(90*turnDir);
				//turn(90);
				move();
			}
		
		}
	}
	
	public void draw(Graphics g)
	{
	

	}






	public void skirmish(Weapon w)
	{

		Unit enemy = getNearestEnemy();


		if (rand>.5) {
			turnDir=3;
		} else {
			turnDir=1;
		}

		if(enemy != null)

		{
			w.use(enemy);	

			if((getDistance(enemy) > getMaxRange()))
			{
				moveTo(enemy);
			}
			else if(getDistance(enemy) < getMaxRange() * .8f)
			{  

				turnTo(enemy);
				turn(90*turnDir);
				//turn(90);
				move();
			}

		}
	}


}



