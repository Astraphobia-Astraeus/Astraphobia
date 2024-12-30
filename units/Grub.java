package teams.student.astraphobia.units;


import java.util.ArrayList;

import components.weapon.Weapon;
import components.weapon.resource.Collector;
import components.weapon.resource.MiningLaser;
import objects.entity.node.Node;
import objects.entity.unit.BaseShip;
import objects.entity.unit.Frame;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import objects.resource.Resource;
import teams.student.astraphobia.*;

public class Grub extends astraphobiaUnit 
{
	int turnDir;
	float rand=(float) Math.random();
	public int enemyX = 0;
	public int enemyY = 0;
	
	
	public Grub(astraphobia p)  
	{
		super(p);
	}
	
	public void design()
	{
		setFrame(Frame.MEDIUM);
		setStyle(Style.WEDGE);
		addWeapon(new MiningLaser(this));
		addWeapon(new MiningLaser(this));
	}

	public void action() 
	{
		harvestNearest(getWeaponOne());
		harvestNearest(getWeaponTwo());
	}

	public void harvestNearest(Weapon w)
	{
		Unit enemy = getNearestEnemy();
		if (getDistance(enemy)>getMaxRange()*3) {
		harvest(getNearestNode(), w);
		} else {
			attack(w);
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
