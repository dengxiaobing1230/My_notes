# Git
## Git创建本地版本库
git init    初始化Git仓库  
git add filename [filename1] ...    将文件添加到仓库  
git status  查看仓库的状态   
git commit -m "message" 将文件提交到仓库  
## 版本回退  
git log     查看从近到远的提交日志  
git reset -hard commit_id  版本回退
git reflog  查看历史版本操作  
## 管理差异
git diff    可以查看工作区和暂存区的差异   
git diff head -- filename 工作区里的文件和版本库里的差异
## 撤销修改 
git checkout -- filename 撤销未提交的修改或者返回到上一次暂存区的状态（丢弃工作区的修改）  
>场景1：当你改乱了工作区某个文件的内容，想直接丢弃工作区的修改时，用命令git checkout -- file。

>场景2：当你不但改乱了工作区某个文件的内容，还添加到了暂存区时，想丢弃修改，分两步，第一步用命令git reset HEAD <file>，就回到了场景1，第二步按场景1操作。

>场景3：已经提交了不合适的修改到版本库时，想要撤销本次提交，参考版本回退一节，不过前提是没有推送到远程库。
## 删除文件  
git rm filename 然后 git commit -m "message";
## 连接远程GitHub仓库  
git remote add origin git仓库SSH地址 添加远程仓库地址  
git push -u origin master 将本地文件上传到远程仓库  
git pull origin master 从远程仓库上拉取  
>如果远程仓库和本地仓库不一致,需要合并的话,就需要在本地项目目录下:   
>git pull origin master -allow-unrelated-histories  跳过这个警告  
