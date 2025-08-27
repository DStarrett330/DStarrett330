## Lab 01

- Name: Drew Starrett
- Email: starrett.10@wright.edu

## Part 1 - GitHub Profile

1. [DStarrett330 GitHub Profile](https://github.com/DStarrett330)

## Part 2 - Research

| Windows | Linux / Mac | Action |
| ---     | ---         | ---    |
| help    | man         |Displays help about Windows PowerShell cmdlets and concepts        |
| Get-Location | pwd    |Displays the full path of the current directory        |
| Get-ChildItem | ls    |Gets the items in one or more specified locations        |
| mkdir   | mkdir       |Creates a new directory        |
| Set-Location | cd     |Sets the working location to a specified location        |
| New-Item | touch      |Creates a new item and sets its value        |
| Move-Item | mv        |Moves an item from one location to another        |
| Copy-Item | cp        |Copies an item from one location to another location        |
| Remove-Item | rm      |Deletes files and directories        |
| notepad.exe | vim     |Launches notepad application        |

## Part 3 - Command Line Navigation

My OS is:
- [x] Windows
- [] Linux
- [] Mac

My Command Line Shell is: Powershell

### Navigating My OS on the Command Line

1. Full / absolute path to your user's home directory: PS C:\Users\drews>
2. Create a directory named `DirA`: New-Item -Name "DirA" -ItemType Directory
3. Create a directory named `Dir B`: New-Item -Name "Dir B" -ItemType Directory
4. Go into `DirA`: cd DirA
5. Go into `Dir B` from `DirA`: cd ..\"Dir B"
6. Return to your user's home directory: cd..
7. Create a file named `test.txt`: New-Item -Name "test.txt"
8. Move the file named `test.txt` into `DirA`: Move-Item -Path test.txt -Destination DirA
9. Contents of `test.txt`: Set-Content -Path C:\Users\drews\DirA\test.txt "I will be the only cs major not in a homeless shelter pleeeeeeease"
```
I will be the only cs major not in a homeless shelter pleeeeeeease
```
10. Make a copy of `test.txt` named `copy.txt` in `DirA`: Copy-Item -Path .\test.txt -Destination .\copy.txt
11. View the contents of `DirA`: ls DirA
12. Make a copy of `test.txt` in `Dir B` named `fodder.txt`: Copy-Item -Path .\test.txt -Destination C:\Users\drews\"Dir B"\fodder.txt
13. Delete / remove both `fodder.txt` AND `Dir B`: Remove-Item "Dir B"

## Citations

To add citations, provide the site and a summary of what it assisted you with.  If generative AI was used, include which generative AI system was used and what prompt(s) you fed it.

https://learn.microsoft.com/en-us/powershell/module/microsoft.powershell.management/set-content?view=powershell-7.5 \
Was used to figure out how to add content to the test file \
https://petri.com/move-file-folder-from-command-line-powershell/#:~:text=In%20the%20PowerShell%20console%2C%20type,path%20of%20the%20target%20folder. \
Was used to figure out how to move files



