package day3;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Main {

	public static void main(String[] args) {
		// SquareFeetGenerator gen = new SquareFeetGenerator();
		// System.out.println(gen.generate()); //117948

		Map<Integer, Rectangle> rects = new HashMap<Integer, Rectangle>();// 1218
		Map<Integer, Boolean> intersects = new HashMap<Integer, Boolean>();// 1218

		int[][] rectList = new int[1000][1000]; // 98005
		int total = 0;
		int id = 1;

		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("src/day3/input1.txt")));
			String line;
			String[] split1;
			String[] split2;
			String[] xAndY;
			String[] wAndH;

			int x = 0, y = 0, w = 0, h = 0;
			int currId = 0;

			while ((line = reader.readLine()) != null) {

				split1 = line.split("@"); // Split it to divide the number from the dimensions!
				currId = Integer.parseInt(split1[0].split("#")[1].split(" ")[0]);
				split2 = split1[1].split(" "); // Split the rest to get x, y and w x h
				xAndY = split2[1].split(",");
				wAndH = split2[2].split("x");
				x = Integer.parseInt(xAndY[0]);
				y = Integer.parseInt(xAndY[1].split(":")[0]);
				w = Integer.parseInt(wAndH[0]);
				h = Integer.parseInt(wAndH[1]);

				for (int dx = 0; dx < w; dx++) {
					for (int dy = 0; dy < h; dy++) {
						rectList[x + dx][y + dy] += 1;
					}
				}

				rects.put(currId, new Rectangle(x, y, w, h));

			}

			for (int i = 0; i < rectList.length; i++) {
				for (int j = 0; j < rectList.length; j++) {
					if (rectList[i][j] >= 2) {
						total += 1;
					}
				}
			}
			reader.close();

			Rectangle batch;
			Rectangle batch2;
			Set<Integer> keySet = rects.keySet();
			int claimId = 0;

			Map<Integer, Rectangle> rects2 = new HashMap<Integer, Rectangle>();// 1218

			while (!rects.isEmpty()) {
				batch = rects.get(claimId);
				if (batch != null) {
					for (int claimId2 : rects.keySet()) {
						batch2 = rects.get(claimId2);
						if (batch.intersects(batch2) && batch != batch2) {
							System.out.println("They intersect!");
							intersects.put(claimId, true);
							intersects.put(claimId2, true);
						}
					}
					if (intersects.get(claimId) == null) {
						id = claimId;
					}

					rects.remove(claimId);
				}
				claimId++;
			}

			System.out.println(total); // 1000000
			System.out.println(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
