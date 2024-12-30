package teams.student.astraphobia.units;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import components.upgrade.Plating;
import components.upgrade.Shield;
import components.weapon.Weapon;
import components.weapon.energy.*;
import components.weapon.explosive.*;
import components.weapon.kinetic.*;
import components.weapon.resource.Collector;
import components.weapon.resource.MiningLaser;
import components.weapon.utility.AntiMissileSystem;
import engine.states.Game;
import objects.entity.unit.BaseShip;
import objects.entity.unit.Frame;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import player.Player;
import teams.student.astraphobia.*;

public class HeChuggin extends astraphobiaUnit {
	
	public boolean s1 = false;
	public boolean p1 = false;
	public boolean st1 = false;
	public boolean s_e = false;
	public boolean m_m = false;
	int turnDir;
	
	public boolean railgun = false;
	public boolean machineGunandAuto = false;
	public boolean lmao = false;
	public int enemyX = 0;
	public int enemyY = 0;
		
	public HeChuggin(astraphobia p)  
	{
		super(p);
	}
	
	public final Unit TouchMe()
	{		
		return getNearestAlly(Fast.class);
	}
	public void design()
	{
		setFrame(Frame.MEDIUM);
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
		else
		{
			addUpgrade(new Shield(this));
		}
		if(usingShield)
		{
			if(!usingExplosive) 
			{
				addWeapon(new Brightlance(this));
				railgun = true;
			}
			else
			{
				addWeapon(new Brightlance(this));
			}
		}
		else if(usingPlating)
		{
			if(amountOfLights > 1)
			{
				addWeapon(new MachineGun(this));
				addWeapon(new Autocannon(this));
				machineGunandAuto = true;
			}
			else
			{
				lmao = true;
				addWeapon(new Megacannon(this));
			}
		}
		else if(usingStructure)
		{
				if(amountOfLights > 1) {
				addWeapon(new MachineGun(this));
				addWeapon(new Autocannon(this));
				machineGunandAuto = true;
				}
				else
				{
					lmao = true;
					addWeapon(new Megacannon(this));
				}
		}	
		else 
		{
			addWeapon(new MachineGun(this));
			addWeapon(new Autocannon(this));
			machineGunandAuto = true;
		}
	}

	public void action() 
	{
		turnAngle();
	
//		averageDPS(getPlayer().getOpponent());
//		averageDPS(getPlayer());
	

//		System.out.println("Average TTK: " + averageTTK(getPlayer().getOpponent()) + " s");
//		System.out.println("average enemy DPS: " + averageDPS(getPlayer().getOpponent()));
//		System.out.println("average ally DPS: " + averageDPS(getPlayer()));
	
		
		
		movement();
		if (usingExplosive) {
			//gravity();
			attack(getWeaponOne());
		}
		if(lmao || railgun)
		{
			attack(getWeaponOne());
		}
		else
		{
			attack(getWeaponOne());
			attack(getWeaponTwo());
		}
	}
	public void turnAngle()
	{
		float rand = (float) Math.random();
		if(rand > .5)
		{
			turnDir = 1;
		}
		else if(rand <= .5)
		{
			turnDir = 3;
		}
		else
		{
			turnDir = 1;
		}
	}
	
	public void draw(Graphics g)
	{
		if(getEnemies().size() > 1)
		{
		g.drawLine(this.getCenterX(), this.getCenterY(), enemyX, enemyY);
		}
	}
	
	
	
	
	public void attack(Weapon w) {
		Unit enemy = getLowestUnitExclude(getPlayer().getOpponent(), BaseShip.class, getMaxRange()+50);
		ArrayList<Unit> enemys = getEnemies();
		if(w == null)
		{
			return;
		}
	
		if(enemys.size() == 1)
		{
			if(countAlliesInRadius(400)  >= 15)
			{
				if(getDistance(getEnemyBase()) < 100)
				{
					w.use(getEnemyBase());
				}
				else
				{
					w.use(getEnemyBase());
				}
			}
			else if(getDistance(getHomeBase())  < 300 && getDistance(getEnemyBase()) < 400)
			{
				w.use(getEnemyBase());
			}
			else
			{
				w.use(getEnemyBase());
			}	
		}
		int enemiesInRadius = countEnemiesInRadius(getMaxRange() + 150);
		int alliesInRadius = countAlliesInRadius(getMaxRange() + 300);
		Unit ally = getNearestAlly();
		ArrayList<Unit> allAllies = getAllies();

		if(enemy != null && w !=null)
		{
			w.use(enemy);	
			
			if(((getDistance(getEnemyBase())) < (getEnemyBase().getMaxRange() * 1.3f)) && countAlliesInRadius(600) < 15)
			{
				w.use(enemy);
			}
			if(enemys.size() > 1)
			{
				if(countAlliesInRadius(2500) >= 15)
				{
					w.use(enemy);	
				}
			}
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
		
		if(((getDistance(getEnemyBase())) < (getEnemyBase().getMaxRange() * 1.3f)) && countAlliesInRadius(600) < 15)
		{
			turnTo(getEnemyBase());
			turn(90);
			move();
		}
		
		if(enemys.size() == 1)
		{
			if(countAlliesInRadius(400)  >= 15)
			{
				if(getDistance(getEnemyBase()) < 100)
				{
					turnTo(getEnemyBase());
					turn(90 * turnDir);
					move();
				}
				else
				{
					moveTo(getEnemyBase());
				}
			}
			else if(getDistance(getHomeBase())  < 300 && getDistance(getEnemyBase()) < 400)
			{
				turnTo(getEnemyBase());
				turn(90 * turnDir);
				move();
			}
			else
			{
				moveTo(getHomeBase());
			}	
		}
		int enemiesInRadius = countEnemiesInRadius(getMaxRange() + 150);
		int alliesInRadius = countAlliesInRadius(getMaxRange() + 300);
		Unit ally = getNearestAlly();
		ArrayList<Unit> allAllies = getAllies();
		float pOrbitr = 0;  

		if(railgun)
		{
			pOrbitr = .67f;
		}
		if(machineGunandAuto)
		{
			pOrbitr = .85f;
		}
		if(enemy == null)
		{
			enemy = getLowestUnitExclude(getPlayer().getOpponent(), BaseShip.class, getMaxRange()+7500);
			if(enemy==null) 
			{
				moveTo(getHomeBase());
			}
		}
		if(countAlliesInRadius(getMaxRange()*3) <= 4 && enemiesInRadius<=4 && enemy!=null && enemy.isInBounds()) 
		{
			
			
			
			turnTo(enemy);
			turnAround();
			for(int i = 0; i < allAllies.size(); i ++)
			{
				if(!allAllies.get(i).hasWeapon(Collector.class) || !allAllies.get(i).hasWeapon(MiningLaser.class) &&
				  ((allAllies.get(i).getDistance(allAllies.get(i).getCenterX(),allAllies.get(i).getCenterY()))> getMaxRange() * 3))
				{
					moveTo(allAllies.get(i));
				}
			}
			
			
			
		}
		else if(enemy != null)
		{
			
			if((getDistance(getEnemyBase()) > getEnemyBase().getMaxRange() * 1.3f))
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
	
			if(enemys.size() > 1)
			{
				if(countAlliesInRadius(2500) >= 15)
				{
					moveTo(getEnemyBase());
				}
			}
		}
	}
	
	
	
	
	public void skirmish(Weapon w)
	{
		float pOrbitr = 0;  
		Unit enemy = getNearestEnemy();
		Unit ally = getNearestAlly();
		double f = 0;
		double G = 6.673e-11;
		if(getDistance(ally) < getMaxRange()/4)
		{
			//repulsion
		}
		if(enemy != null)
		{
			
			if(usingPlating)
			{
				pOrbitr = .85f;
			}
			if(usingExplosive)
			{
				pOrbitr = .6f;
			}
			w.use(enemy);	
	
			if((getDistance(enemy) > getMaxRange()))
			{
				moveTo(enemy);
			}
			else if(getDistance(enemy) < getMaxRange() * pOrbitr)
			{			
				turnTo(enemy);
				//turn(90*turnDir);
				turn(90);
				move();
			}	
		}
	}
}