package teams.student.astraphobia.units;

import components.upgrade.Shield;
import components.weapon.Weapon;
import components.weapon.energy.SmallLaser;
import objects.entity.unit.BaseShip;
import objects.entity.unit.Frame;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.astraphobia.*;

public class Bait extends astraphobiaUnit{
	
	public Bait(astraphobia p) 
	{
		super(p);
	}
	public void design()
	{
		setFrame(Frame.LIGHT);
		setStyle(Style.WEDGE);
		addUpgrade(new Shield(this));
		addWeapon(new SmallLaser(this));
	}
	public void action() 
	{
		skirmish(getWeaponOne());
	}	
	public void attack(Weapon w) {
		Unit enemy = getLowestUnitExclude(getPlayer().getOpponent(), BaseShip.class, getMaxRange()+50);
		if(enemy == null)
		{
			enemy = getNearestEnemy();
		}
		if(enemy != null)
		{
			w.use(enemy);	
			if((getDistance(enemy) > getMaxRange()))
			{
				moveTo(enemy);
			}
			else if(getDistance(enemy) < getMaxRange() * .85f)
			{ 
				turnTo(enemy);
				turnAround();
				move();
			}
		}
	}
}


