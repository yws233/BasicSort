package test;

import java.util.Arrays;
import java.util.Random;

/**
 * 八大基础排序
 * @author ws
 */

public class BasicSort {
    
    /**
    *  插入排序
    /*
    public static void insertionSort(int[] arr){
        for( int i=0; i<arr.length-1; i++ ) {
            for( int j=i+1; j>0; j-- ) {
                if( arr[j-1] <= arr[j] )
                    break;
                int temp = arr[j];      //交换操作
                arr[j] = arr[j-1];
                arr[j-1] = temp;
                System.out.println("Sorting:  " + Arrays.toString(arr));
            }
        }
    }

    /**
     * 希尔排序
     * */

    public static void shellSort(int[] arr){
        int gap = arr.length / 2;
        for (; gap > 0; gap /= 2) {      //不断缩小gap，直到1为止
            for (int j = 0; (j+gap) < arr.length; j++){     //使用当前gap进行组内插入排序
                for(int k = 0; (k+gap)< arr.length; k += gap){
                    if(arr[k] > arr[k+gap]) {
                        int temp = arr[k+gap];      //交换操作
                        arr[k+gap] = arr[k];
                        arr[k] = temp;
                        System.out.println("Sorting: " + Arrays.toString(arr));
                    }
                }
            }
        }
    }

    /*
    * 选择排序
    * */
    public static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {    //选出之后待排序中值最小的位置
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }
            if (min != i) {
                int temp = arr[min];      //交换操作
                arr[min] = arr[i];
                arr[i] = temp;
                System.out.println("Sorting:  " + Arrays.toString(arr));
            }
        }
    }

    /*
    *
    * 堆排序
    * */
    public static void heapSort(int[] arr){
        for(int i = arr.length; i > 0; i--){
            max_heapify(arr, i);
            int temp = arr[0];      //堆顶元素(第一个元素)与Kn交换
            arr[0] = arr[i-1];
            arr[i-1] = temp;
        }
    }
    private static void max_heapify(int[] arr, int limit){
        if(arr.length <= 0 || arr.length < limit) return;
        int parentIdx = limit / 2;
        for(; parentIdx >= 0; parentIdx--){
            if(parentIdx * 2 >= limit){
                continue;
            }
            int left = parentIdx * 2;       //左子节点位置
            int right = (left + 1) >= limit ? left : (left + 1);    //右子节点位置，如果没有右节点，默认为左节点位置
            int maxChildId = arr[left] >= arr[right] ? left : right;
            if(arr[maxChildId] > arr[parentIdx]){   //交换父节点与左右子节点中的最大值
                int temp = arr[parentIdx];
                arr[parentIdx] = arr[maxChildId];
                arr[maxChildId] = temp;
            }
        }
        System.out.println("Max_Heapify: " + Arrays.toString(arr));
    }

    /*
    * 冒泡排序
    * */
    public static void bubbleSort(int[] arr){
        for (int i = arr.length; i > 0; i--) {      //外层循环移动游标
            for(int j = 0; j < i && (j+1) < i; j++){    //内层循环遍历游标及之后(或之前)的元素
                if(arr[j] > arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    System.out.println("Sorting: " + Arrays.toString(arr));
                }
            }
        }
    }

    /*
    *
    * 快速排序
    * */
    public static void quickSort(int[] arr, int low, int high){
        if(arr.length <= 0) return;
        if(low >= high) return;
        int left = low;
        int right = high;
        int temp = arr[left];   //挖坑1：保存基准的值
        while (left < right){
            while(left < right && arr[right] >= temp){  //坑2：从后向前找到比基准小的元素，插入到基准位置坑1中
                right--;
            }
            arr[left] = arr[right];
            while(left < right && arr[left] <= temp){   //坑3：从前往后找到比基准大的元素，放到刚才挖的坑2中
                left++;
            }
            arr[right] = arr[left];
        }
        arr[left] = temp;   //基准值填补到坑3中，准备分治递归快排
        System.out.println("Sorting: " + Arrays.toString(arr));
        quickSort(arr, low, left-1);
        quickSort(arr, left+1, high);
    }

    /*
    * 归并排序
    * */
    public static int[] mergingSort(int[] arr){
        if(arr.length <= 1) return arr;
        int num = arr.length >> 1;
        int[] leftArr = Arrays.copyOfRange(arr, 0, num);
        int[] rightArr = Arrays.copyOfRange(arr, num, arr.length);
        System.out.println("split two array: " + Arrays.toString(leftArr) + " And " + Arrays.toString(rightArr));
        return mergeTwoArray(mergingSort(leftArr), mergingSort(rightArr));      //不断拆分为最小单元，再排序合并
    }
    private static int[] mergeTwoArray(int[] arr1, int[] arr2) {
        int i = 0, j = 0, k = 0;
        int[] result = new int[arr1.length + arr2.length];  //申请额外的空间存储合并之后的数组
        while (i < arr1.length && j < arr2.length) {      //选取两个序列中的较小值放入新数组
            if (arr1[i] <= arr2[j]) {
                result[k++] = arr1[i++];
            } else {
                result[k++] = arr2[j++];
            }
        }
        while (i < arr1.length) {     //序列1中多余的元素移入新数组
            result[k++] = arr1[i++];
        }
        while (j < arr2.length) {     //序列2中多余的元素移入新数组
            result[k++] = arr2[j++];
        }
        System.out.println("Merging: " + Arrays.toString(result));
        return result;
    }

    /*
    * 基数排序
    * */
    public static void radixSort(int[] arr){
        if(arr.length <= 1) return;
        //取得数组中的最大数，并取得位数
        int max = 0;
        for(int i = 0; i < arr.length; i++){
            if(max < arr[i]){
                max = arr[i];
            }
        }
        int maxDigit = 1;
        while(max / 10 > 0){
            maxDigit++;
            max = max / 10;
        }
        System.out.println("maxDigit: " + maxDigit);
        //申请一个桶空间
        int[][] buckets = new int[10][arr.length];
        int base = 10;
        //从低位到高位，对每一位遍历，将所有元素分配到桶中
        for(int i = 0; i < maxDigit; i++){
            int[] bktLen = new int[10];        //存储各个桶中存储元素的数量

            //分配：将所有元素分配到桶中
            for(int j = 0; j < arr.length; j++){
                int whichBucket = (arr[j] % base) / (base / 10);
                buckets[whichBucket][bktLen[whichBucket]] = arr[j];
                bktLen[whichBucket]++;
            }
            //收集：将不同桶里数据挨个捞出来,为下一轮高位排序做准备,由于靠近桶底的元素排名靠前,因此从桶底先捞
            int k = 0;
            for(int b = 0; b < buckets.length; b++){
                for(int p = 0; p < bktLen[b]; p++){
                    arr[k++] = buckets[b][p];
                }
            }
            System.out.println("Sorting: " + Arrays.toString(arr));
            base *= 10;
        }
    }

    public static void main(String[] args) {
        int count = 0;
        int[] nums = new int[10000];
        for (int i = 0; i < nums.length; i++){
            Random random = new Random();
            int number = random.nextInt(1000);
            nums[i] = number;
            System.out.println(nums[i]);
            count++;
        }
     /*   long open = System.currentTimeMillis();
        System.out.println("插入排序>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        insertionSort(nums);
        long close = System.currentTimeMillis();
        System.out.println((close - open)/1000 + "s");*/

        /*long open = System.currentTimeMillis();
        System.out.println("希尔排序>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        shellSort(nums);
        long close = System.currentTimeMillis();
        System.out.println((close - open)/1000 + "s");
        System.out.println(count + "个数据");*/
       /* long open = System.currentTimeMillis();
        System.out.println("选择排序>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        selectionSort(nums);
        long close = System.currentTimeMillis();
        System.out.println((close - open)/1000 + "s");*/
/*
        long open = System.currentTimeMillis();
        System.out.println("堆排序>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        heapSort(nums);
        long close = System.currentTimeMillis();
        System.out.println((close - open)/1000 + "s");
        System.out.println(count + "次");*/

      /*  long open = System.currentTimeMillis();
        System.out.println("冒泡排序>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        bubbleSort(nums);
        long close = System.currentTimeMillis();
        System.out.println((close - open)/1000 + "s");
        System.out.println(count + "次");*/

     /*   long open = System.currentTimeMillis();
        System.out.println("快速排序>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        quickSort(nums,0, nums.length-1);
        long close = System.currentTimeMillis();
        System.out.println((close - open)/1000 + "s");
        System.out.println(count + "个数据");*/



        long open = System.currentTimeMillis();
        System.out.println("基数排序>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        mergingSort(nums);
        long close = System.currentTimeMillis();
        System.out.println((close - open)/1000 + "s");
        System.out.println(count + "个数据");

    }
}
