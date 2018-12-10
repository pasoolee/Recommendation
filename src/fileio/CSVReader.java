package fileio;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CSVReader {
	
	private String _fileName;
	
	private String[] _headerOfFile;
	
	private final ArrayList<String[]> _fileData;
	
	public CSVReader(String fileName) throws IOException
	{

		this._fileName = fileName;
		_fileData = new ArrayList<String[]>();		
		
		ReadCSVFile();
	}
	
	public HashMap<String, Integer> getHeader()
	{
		HashMap<String, Integer> forReturn = new HashMap<String, Integer>();
		
		for(int i = 0; i < _headerOfFile.length; i++)
		{
			forReturn.put(_headerOfFile[i], i);
		}
		
		return forReturn;		
	}
	
	public ArrayList<String[]> getData()
	{
		ArrayList<String[]> forReturn = new ArrayList<String[]>();
		
		for (int i = 0; i < _fileData.size(); i++)
		{
			String[] temp = new String[_fileData.get(i).length];
			
			for (int j = 0; j < _fileData.get(i).length; j++)
			{
				temp[j] = _fileData.get(i)[j];
			}
			
			forReturn.add(temp);
		}
		
		return forReturn;
	}

	public void ReadCSVFile() throws IOException
	{
		Path path = Paths.get(this._fileName);
		
		List<String> contents = Files.readAllLines(path, StandardCharsets.UTF_8);
		
		if (contents.size() > 0)
		{
			String lineContent = contents.get(0);
			_headerOfFile = lineContent.split(",");
			
			for (int i = 1;i < contents.size(); i++)
			{
				lineContent = contents.get(i);
				
				if (lineContent.length() > 0)
				{
					String[] splittedLine = lineContent.split(",");
					
					if (splittedLine.length == _headerOfFile.length)
					{
						_fileData.add(splittedLine);
					}
					else
					{
						continue;
					}
				}
				else
				{
					continue;
				}
					
			}
			
		}
		else
		{
			throw new IllegalArgumentException("File is empty.");
		}
		
	}
}
