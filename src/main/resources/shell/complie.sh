#! /bin/bash
#$1为需要编译的文件的路径 $2为命名的序号-1
#根据输入参数来判断
path="/Oj/compile/"
#linux下文件路径

if [ ! "$2" ]
  then exit
fi
input_file=$1
output_file=$path
output_file2=$path$2
#判断代码语言
# shellcheck disable=SC2034
languageCode=${input_file#*.}
echo $languageCode
if [ "$languageCode" = "java" ]
then
  echo "$(javac $input_file -d $output_file)"

elif [ "$languageCode" = "gcc" ]
then
  echo "$(gcc $input_file -o $output_file2)"

elif [ "$languageCode" = "python" ]
then
  echo "$(mv $input_file $output_file2)"
#错误情况
else
  echo "error"
fi
