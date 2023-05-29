#! /bin/bash
#参数个数<2 exit
if [ ! "$2" ]
then exit
fi
#为*就是无输入代码题
if [ "$2" = "*" ]
then
  filePath=$1
else
  filePath=$1
  param=$2
fi
#判断代码
javaC=$(echo $filePath | grep "Main")
cC=$(echo $filePath |grep "gcc")
pythonC=$(echo $filePath | grep "python")

#开始执行
if [[ "$javaC" != "" ]]
then
  Main="Main"
  #得到绝对路径
  new_filePath="${filePath%/*}"
  echo $param|echo "$(java -cp $new_filePath $Main)"
fi
#执行可执行c文件，并且通过管道将参数传递给该文件
if [[ "$cC" != "" ]]
then
  echo $param|$filePath
fi

if [[ "$pythonC" != "" ]]
then
  echo $param|echo  "$(python $filePath )"
fi
