package teams.student.astraphobia.units;

import components.upgrade.Shield;
import components.weapon.Weapon;
import components.weapon.kinetic.Railgun;
import objects.entity.unit.Frame;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.astraphobia.*;

public class Finisher extends astraphobiaUnit 
{
	
	public Finisher(astraphobia p)  
	{
		super(p);
	}
	
	public void design()
	{
		setFrame(Frame.MEDIUM);
		setStyle(Style.DAGGER);
		addWeapon(new Railgun(this));
		addUpgrade(new Shield(this));
	}

	public void action() 
	{
		skirmish(getWeaponOne());
	}	
	public void skirmish(Weapon w)
	{
		Unit enemy = getNearestEnemy();
		
		if(enemy != null)
		{
			w.use(enemy);	
			
			if(getDistance(enemy) > getMaxRange()-10)
			{
				moveTo(enemy);
			}
			
			else if (getDistance(enemy) <= 8 && isInBounds())	{
				turnTo(enemy);
				turnAround();
				move();
			}
			else
			{
				move();
			}
		}
	}
}
