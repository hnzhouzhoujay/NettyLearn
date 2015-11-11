package com.zj.test.hadoop;

import java.io.IOException;
import java.util.Collections;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class DeDup extends Configured implements Tool {
	public static class Map extends Mapper<LongWritable, Text, Text, Text>{
		private final IntWritable INT_1=new IntWritable(1);
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			//当输入文件格式为TextInputFormat时,默认的输入 为<LongWirtable,Text>
			//设置TextInputFormat，会生成InputSplit（产生分片），当分片数据传给TextInputFormat
			//由TextInputFormat产生供map<key,value>
			String line=value.toString();
			context.write(value, new Text(""));
		}
		
	}
	public static class Reduce extends Reducer<Text, IntWritable, Text, Text>{

		@Override
		protected void reduce(Text key, Iterable<IntWritable> values,
				Context context)
				throws IOException, InterruptedException {
			context.write(key, new Text(""));
		}
	}
	@Override
	public int run(String[] arg0) throws Exception {
		Job job =new Job(getConf());
		
		job.setJarByClass(DeDup.class);
		job.setJobName("wordcount");
		//设定输出
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		job.setCombinerClass(Reduce.class);
		//设定Mapper ,Reducer
		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);
		//设定输入文件格式，输出文件格式
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		//设定输出文件
		FileInputFormat.setInputPaths(job, new Path(arg0[0]));
		FileOutputFormat.setOutputPath(job, new Path(arg0[1]));
		//等待完成
		boolean success=job.waitForCompletion(true);
		
		return success?0:1;
	}
	public static void main(String[] args) {
		try {
			int ret=ToolRunner.run(new DeDup(), args);
			System.exit(ret);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
