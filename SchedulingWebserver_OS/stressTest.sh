curl -s "http://localhost:4567/textFiles/file1.txt?[1-10]" &
pidlist='$pidlist $!'
curl -s "http://localhost:4567/textFiles/file2.txt?[1-10]" &
pidlist='$pidlist $!'
curl -s "http://localhost:4567/textFiles/file3.txt?[1-10]" &
pidlist='$pidlist $!'
curl -s "http://localhost:4567/textFiles/file4.txt?[1-10]" &
pidlist='$pidlist $!'
curl -s "http://localhost:4567/textFiles/file5.txt?[1-10]" &
pidlist='$pidlist $!'
curl -s "http://localhost:4567/textFiles/file6.txt?[1-10]" &
pidlist='$pidlist $!'
curl -s "http://localhost:4567/textFiles/file7.txt?[1-10]" &
pidlist='$pidlist $!'
curl -s "http://localhost:4567/music.mp3?[1-10]" &
pidlist='$pidlist $!'
