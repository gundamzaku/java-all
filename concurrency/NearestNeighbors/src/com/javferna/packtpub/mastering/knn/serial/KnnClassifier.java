package com.javferna.packtpub.mastering.knn.serial;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.javferna.packtpub.mastering.knn.data.Distance;
import com.javferna.packtpub.mastering.knn.data.Sample;
import com.javferna.packtpub.mastering.knn.distances.EuclideanDistanceCalculator;

/**
 * Serial implementation of the Knn algorithm
 * @author author
 */
public class KnnClassifier {

	/**
	 * List of train data
	 */
	private final List<? extends Sample> dataSet;
	
	/**
	 * K parameter
	 */
	private int k;
	
	/**
	 * Constructor of the class. Initialize the internal data
	 * @param dataSet Train data
	 * @param k K parameter
	 */
	public KnnClassifier(List<? extends Sample> dataSet, int k) {
		//初始化
		this.dataSet=dataSet;
		this.k=k;
	}
	
	/**
	 * Method that classifies an example
	 * @param example Example to classify
	 * @return The tag or class of the example
	 * @throws Exception Exception if something goes wrong
	 */
	public String classify (Sample example) {
		
		Distance[] distances=new Distance[dataSet.size()];
		
		int index=0;
		//建立字典
		for (Sample localExample : dataSet) {
			distances[index]=new Distance();
			distances[index].setIndex(index);
			//计算距离，与我新建立的test来对比距离
			distances[index].setDistance(EuclideanDistanceCalculator.calculate(localExample, example));
			index++;
		}
		//排序
		Arrays.sort(distances);
		//建立map
		Map<String, Integer> results = new HashMap<>();
		//这就是取最近的十条
		for (int i = 0; i < k; i++) {
		  Sample localExample = dataSet.get(distances[i].getIndex());
		  //得到标签
		  String tag = localExample.getTag();
		  /*
		  如果key存在，则执行lambda表达式，表达式入参为oldVal和newVal(neVal即merge()的第二个参数)。表达式返回最终put的val。如果key不存在，则直接putnewVal。
		   */
		  /*
		  String k = "key";
		  HashMap<String, Integer> map = new HashMap<String, Integer>() {{
			put(k, 1);
		  }};
		  Integer newVal = 2;
		  if(map.containsKey(k)) {
		  	map.put(k, map.get(k) + newVal);
		  } else {
		  	map.put(k, newVal);
		  }*/
		  results.merge(tag, 1, (a, b) -> a+b);
		}

		//用于返回给定collection的最大元素,根据其元素的自然顺序
		
		return Collections.max(results.entrySet(),
			    Map.Entry.comparingByValue()).getKey();
	}
}
