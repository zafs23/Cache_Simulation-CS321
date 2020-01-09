import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;

/**
 * Test class reads a file and test one-level or two-level Cache implementation
 * and prints the references, hits and Cache ratio.
 * 
 * @author SajiaZafreen
 *
 */
public class Test {
	/**
	 * Driver method for testing one-level or two-level Cache implementation.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String input = "";
		if (args.length == 3) {
			// creating one-level-cache
			if (args[0].equals("1")) {
				if (args[1].matches("[0-9]+")) {
					String fileName = args[2].toString();
					int size = Integer.parseInt(args[1].toString());
					try {
						input = new String(Files.readAllBytes(Paths.get(fileName)));
						createCacheOne(input, size);
					} catch (IOException e) {
						System.out.println("File not Found");
						printUsage();
						System.exit(1);
					}
				} else {
					System.out.println("Cache entries must be a number");
					printUsage();
					System.exit(1);
				}
			} else {
				System.out.println("Only 1 or 2 is acceptable as Cache Levels");
				printUsage();
				System.exit(1);
			}

		} else if (args.length == 4) {
			// creating two-level-cache
			if (args[0].equals("2")) {
				if (args[1].matches("[0-9]+") && args[2].matches("[0-9]+")) {// + means one or more times
					String fileName = args[3].toString();
					int size1 = Integer.parseInt(args[1].toString());
					int size2 = Integer.parseInt(args[2].toString());
					if (size1 < size2) {
						try {
							input = new String(Files.readAllBytes(Paths.get(fileName)));
							createCacheTwo(input, size1, size2);
						} catch (IOException e) {
							System.out.println("File not Found");
							printUsage();
							System.exit(1);
						}
					} else {
						System.out.println("\nLevel-one-Cache size must be lower than Level-two-Cache size\n");
						printUsage();
						System.exit(1);
					}
				} else {
					System.out.println("Cache entries must be a number");
					printUsage();
					System.exit(1);
				}
			} else {
				System.out.println("Only 1 or 2 is acceptable as Cache Levels");
				printUsage();
				System.exit(1);
			}
		} else {
			printUsage();
			System.exit(1);
		}
	}

	/**
	 * Tests two-level Cache implementation
	 * 
	 * @param input is the string input
	 * @param size1 is the number of 1st-level cache entries
	 * @param size2 is the number of 2nd-level cache entries
	 */
	private static void createCacheTwo(String input, int size1, int size2) {
		Cache<String> levelOneCache = new Cache<String>(size1);
		System.out.println("First Level cache with " + size1 + " entries has been created.");
		Cache<String> levelTwoCache = new Cache<String>(size2);
		System.out.println("Second Level cache with " + size2 + " entries has been created.\n");
		// StringTokenizer stringInput = new StringTokenizer(input, " //\n");//
		// different approach
		String[] parseInput = input.split("\\s+");
		int i = 0;
		int j = 0;
		int cacheOneHit = 0;
		int cacheTwoHit = 0;
		int cacheOneSearch = 0;
		int cacheTwoSearch = 0;
		String fileString;
		// System.out.println(parseInput.length);
		// while (stringInput.hasMoreTokens()) {
		for (int aLength = 0; aLength < parseInput.length; aLength++) {
			// fileString = stringInput.nextToken();
			fileString = parseInput[aLength];
			if (levelOneCache.getCacheLinkedList().contains(fileString)) {
				int index = levelOneCache.getCacheLinkedList().indexOf(fileString);
				levelOneCache.removeFromCache(index);
				levelOneCache.addToCache(fileString);

				levelTwoCache.removeFromCache(index);
				levelTwoCache.addToCache(fileString);
				cacheOneHit++;
			} else if (levelTwoCache.getCacheLinkedList().contains(fileString)) {
				int index = levelTwoCache.getCacheLinkedList().indexOf(fileString);
				levelTwoCache.removeFromCache(index);
				levelTwoCache.addToCache(fileString);

				if (levelOneCache.cacheFull()) {
					levelOneCache.removeLastCache();
					levelOneCache.addToCache(fileString);
				} else {
					levelOneCache.addToCache(fileString);
				}
				cacheTwoHit++;
				j++;// both time searches
			} else {
				j++;
				// add to level one
				if (levelOneCache.cacheFull()) {
					levelOneCache.removeLastCache();
					levelOneCache.addToCache(fileString);
				} else {
					levelOneCache.addToCache(fileString);
				}
				// add to level two
				if (levelTwoCache.cacheFull()) {
					levelTwoCache.removeLastCache();
					levelTwoCache.addToCache(fileString);
				} else {
					levelTwoCache.addToCache(fileString);
				}
			}
			if (parseInput.length < 100) {
				if (aLength % 20 == 0)
					System.out.print(".");
			} else {
				if (aLength % 20000 == 0)
					System.out.print(".");
			}
			i++;
		}
		System.out.println("\n");
		cacheOneSearch = i;
		cacheTwoSearch = j;
		printTwoLevelCache(cacheOneSearch, cacheTwoSearch, cacheOneHit, cacheTwoHit);
	}

	/**
	 * Tests an one-level Cache implementation
	 * 
	 * @param input is the string input
	 * @param size  is the number of Cache entries
	 */
	private static void createCacheOne(String input, int size) {
		Cache<String> oneLevelCache = new Cache<String>(size);
		System.out.println("One-Level cache with " + size + " entries has been created.\n");
		// StringTokenizer stringInput = new StringTokenizer(input, " //\n");
		String[] parseInput = input.split("\\s+");
		int i = 0;
		int totalHits = 0;
		int totalSearch = 0;
		String fileString;
		// while (stringInput.hasMoreTokens()) {
		for (int aLength = 0; aLength < parseInput.length; aLength++) {
			// fileString = stringInput.nextToken();
			fileString = parseInput[aLength];
			if (oneLevelCache.getCacheLinkedList().contains(fileString)) {
				// oneLevelCache.removeFromCache(fileString);
				oneLevelCache.addToCache(fileString);
				totalHits++;
			} else {
				if (oneLevelCache.cacheFull()) {
					oneLevelCache.removeLastCache();
					oneLevelCache.addToCache(fileString);
				} else {
					oneLevelCache.addToCache(fileString);
				}
			}
			if (parseInput.length < 100) {
				if (aLength % 20 == 0)
					System.out.print(".");
			} else {
				if (aLength % 20000 == 0)
					System.out.print(".");
			}
			i++;
		}
		totalSearch = i;// i started from 0
		printLevelOneCache(totalHits, totalSearch);
	}

	/**
	 * Prints one-level cache references, hits and ratio.
	 * 
	 * @param totalHits   is total hits in one-level cache
	 * @param totalSearch is total references in one-level cache
	 */
	private static void printLevelOneCache(int totalHits, int totalSearch) {
		DecimalFormat df = new DecimalFormat(".0000000000000000");
		double cacheHitRatio = 0;
		cacheHitRatio = (double) totalHits / totalSearch;
		System.out.println("\n");
		System.out.println("Total References: " + totalSearch + "\nTotal cache hits: " + totalHits
				+ "\nCache hit ratio: " + df.format(cacheHitRatio));
	}

	/**
	 * Prints the Two-level Cache references, hits and ratios.
	 * 
	 * @param cacheOneSearch is level-one cache search references
	 * @param cacheTwoSearch is level-two cache search references
	 * @param cacheOneHit    is level-one cache hits
	 * @param cacheTwoHit    is level-two cache hits
	 */
	private static void printTwoLevelCache(int cacheOneSearch, int cacheTwoSearch, int cacheOneHit, int cacheTwoHit) {
		DecimalFormat df = new DecimalFormat(".0000000000000000");
		double cacheOneRatio = 0;
		double cacheTwoRatio = 0;
		double globalRatio = 0;
		int totalHit = 0;
		cacheOneRatio = (double) cacheOneHit / cacheOneSearch;
		cacheTwoRatio = (double) cacheTwoHit / cacheTwoSearch;
		totalHit = cacheOneHit + cacheTwoHit;
		globalRatio = (double) totalHit / cacheOneSearch;

		System.out.println();
		System.out.println("Global References: " + cacheOneSearch + "\nGlobal hit: " + totalHit + "\nGlobal hit ratio: "
				+ df.format(globalRatio));
		System.out.println();
		System.out.println(
				"The number of 1st-level references: " + cacheOneSearch + "\nThe number of 1st-level cache hits: "
						+ cacheOneHit + "\nThe 1st-level hit ratio: " + df.format(cacheOneRatio));
		System.out.println();
		System.out.println(
				"The number of 2nd-level references: " + cacheTwoSearch + "\nThe number of 2nd-level cache hits: "
						+ cacheTwoHit + "\nThe 2nd-level hit ratio: " + df.format(cacheTwoRatio));
	}

	/**
	 * Prints Usage
	 */
	private static void printUsage() {
		System.out.println("Usage: $ java Test [1] [Level-one-Cache size] [filename]");
		System.out.println("Usage: $ java Test [2] [Level-one-Cache-size] [Level-two-Cache-size] [filename]");
		System.out.println("1 for one-level-Cache \n2 for two-level Cache");
	}
}
