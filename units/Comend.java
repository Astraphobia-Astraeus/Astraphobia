package teams.student.astraphobia.units;


import java.util.ArrayList;

import components.upgrade.Shield;
import components.weapon.Weapon;
import components.weapon.resource.Collector;
import components.weapon.resource.MiningLaser;
import components.weapon.utility.Aegis;
import components.weapon.utility.CommandRelay;
import components.weapon.utility.SpeedBoost;
import objects.entity.node.Node;
import objects.entity.unit.BaseShip;
import objects.entity.unit.Frame;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import objects.resource.Resource;
import teams.student.astraphobia.*;

public class Comend extends astraphobiaUnit 
{
	int turnDir;
	float rand=(float) Math.random();
	public int enemyX = 0;
	public int enemyY = 0;
	
	
	public Comend(astraphobia p)  
	{
		super(p);
	}
	
	public void design()
	{
		setFrame(Frame.MEDIUM);
		setStyle(Style.BOXY);
		addWeapon(new CommandRelay(this));
		addWeapon(new SpeedBoost(this));
		addUpgrade(new Shield(this));
	}

	public void action() 
	{
		
			Unit enemy = getNearestEnemy();
			Unit nearFast = getNearestUnit(getPlayer(), HeChuggin.class);
			
			moveTo(nearFast);
		
			if(countAlliesInRadius(300) >= 2 && countEnemiesInRadius(1000) >= 1)
			{
				getWeaponOne().use();
			}
		
			
			Unit u = getNearestEnemy();
			
			if(u != null) {
				
				if(getDistance(nearFast) >= 150)
				{
					getWeaponTwo().use();
				}
				
				
//			if(countAlliesInRadius(500) < 2 && countEnemiesInRadius(500) > 2)
//			{
//				getWeaponTwo().use();
//			}
//			else if((getDistance(u) > u.getMaxRange()) && (isDamaged() || getHitTimer() < 5) && (getCurEffectiveHealth() > 0.5f))
//			{
//				getWeaponTwo().use();
//			}
			
			
			
			}
	}
	
	public void comand()
	{
		
		Unit nearFast = getNearestUnit(getPlayer(), HeChuggin.class);
		
		if(nearFast != null)
		{
			if(getDistance(nearFast) < 200)
			{
				move(getAngleToward(nearFast.getX(),nearFast.getY()) + 90);
			}
			else
			{
				moveTo(nearFast);
			
			}
		}
		
		
		
	
		
		
	}
	
	
	public void aegis()
	{
		
		
		Unit ally = getNearestAlly();
		
		if((ally != null))
			{
		if((getDistance(ally) > ally.getMaxRange()) && (isDamaged() || getHitTimer() < 150) && (getCurEffectiveHealth() > 0.5f))
		{
			getWeaponTwo().use();
		}
		
				}
		
	}

	
	
	public void attack(Weapon w) {
		Unit enemy = getLowestUnitExclude(getPlayer().getOpponent(), BaseShip.class, getMaxRange()+50);
		ArrayList<Unit> enemys = getEnemies();
		if (rand>.5) {
			turnDir=3;
		} else {
			turnDir=1;
		}

		int enemiesInRadius = countEnemiesInRadius(getMaxRange() + 150);
		int alliesInRadius = countAlliesInRadius(getMaxRange() + 300);
		Unit ally = getNearestAlly();
		ArrayList<Unit> allAllies = getAllies();

		float pOrbitr = 0;  
		if(enemy == null)
		{
			enemy = getNearestEnemy();
		}
		if(enemy != null && w!=null)
		{
			w.use(enemy);	
			if((getDistance(enemy) > getMaxRange()))
			{
				enemyX = (int) enemy.getCenterX();
				enemyY = (int) enemy.getCenterY();

				moveTo(enemy);
			}
			else if(getDistance(enemy) < getMaxRange() * pOrbitr)
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
	
	public void harvest(Node n, Weapon w)
	{		
		// Approach the node
		if (rand>.5) {
			turnDir=3;
		} else {
			turnDir=1;
		}
		
		if(getDistance(n) > w.getMaxRange())
		{
			moveTo(n);
		}
		// Back up if I'm too close
		else if(getDistance(n) < w.getMaxRange())
		{
			
			turnTo(n);
			turn(90);
			move();
		}
		w.use(n);
		
	}
	
}
