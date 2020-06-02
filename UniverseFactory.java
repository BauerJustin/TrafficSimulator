
public class UniverseFactory {

	private static int level = 0;
	
	public static int getLevel() {
		return level;
	}

	public static void setLevel(int level) {
		UniverseFactory.level = level;
	}

	public static Universe getNextUniverse() {

		level++;
		
		if (level == 1) {
			return new MappedUniverse();
		}
		else {
			return null;
		}

	}
	
}
