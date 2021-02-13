package com.rpgsim.server.util;

import com.rpgsim.common.sheets.Account;
import com.rpgsim.common.FileManager;
import com.rpgsim.common.sheets.PlayerSheet;
import com.rpgsim.common.sheets.SheetModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountManager
{
    private final HashMap<Integer, Account> activeAccounts = new HashMap<>();
    private final File accountFile = new File(FileManager.app_dir + "data\\accounts\\");
    
    /**
     * Check all accounts and its sheets.
     * 
     * @throws java.io.IOException standard I/O exceptions.
     */
    public void checkAccounts() throws IOException
    {
        for (File f : accountFile.listFiles())
        {
            boolean changed = false;
            Account acc = null;
            
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(f)))
            {
                acc = (Account) in.readObject();
                PlayerSheet sheet = acc.getPlayerSheet();
                SheetModel model = SheetManager.getDefaultSheetModel();
                
                if (model.getInfo().length != sheet.getInfo().length)
                {
                    int oldLength = sheet.getInfo().length;
                    sheet.setInfo(Arrays.copyOf(sheet.getInfo(), model.getInfo().length));
                    if (oldLength < sheet.getInfo().length)
                    {
                        for (int i = oldLength; i < sheet.getInfo().length; i++)
                        {
                            sheet.getInfo()[i] = "none";
                        }
                    }
                    changed = true;
                }
                
                if (model.getCurrentStats().length != sheet.getCurrentStats().length)
                {
                    int oldLength = sheet.getCurrentStats().length;
                    sheet.setCurrentStats(Arrays.copyOf(sheet.getCurrentStats(), model.getCurrentStats().length));
                    if (oldLength < sheet.getCurrentStats().length)
                    {
                        for (int i = oldLength; i < sheet.getCurrentStats().length; i++)
                        {
                            sheet.getCurrentStats()[i] = "0";
                        }
                    }
                    
                    changed = true;
                }
                
                if (model.getMaxStats().length != sheet.getMaxStats().length)
                {
                    int oldLength = sheet.getMaxStats().length;
                    sheet.setMaxStats(Arrays.copyOf(sheet.getMaxStats(), model.getMaxStats().length));
                    if (oldLength < sheet.getMaxStats().length)
                    {
                        for (int i = oldLength; i < sheet.getMaxStats().length; i++)
                        {
                            sheet.getMaxStats()[i] = "0";
                        }
                    }
                    changed = true;
                }
                
                if (model.getAttributes().length != sheet.getAttributes().length)
                {
                    int oldLength = sheet.getAttributes().length;
                    sheet.setAttributes(Arrays.copyOf(sheet.getAttributes(), model.getAttributes().length));
                    if (oldLength < sheet.getAttributes().length)
                    {
                        for (int i = oldLength; i < sheet.getAttributes().length; i++)
                        {
                            sheet.getAttributes()[i] = "0";
                        }
                    }
                    
                    changed = true;
                }
                
                if (model.getSkills().length != sheet.getSkills().length)
                {
                    int oldLength = sheet.getSkills().length;
                    sheet.setSkills(Arrays.copyOf(sheet.getSkills(), model.getSkills().length));
                    if (oldLength < sheet.getSkills().length)
                    {
                        for (int i = oldLength; i < sheet.getSkills().length; i++)
                        {
                            sheet.getSkills()[i] = "0";
                        }
                    }
                    
                    changed = true;
                }
            }
            catch (ClassNotFoundException ex)
            {
                Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (changed)
            {
                try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f)))
                {
                    out.writeObject(acc);
                }
            }
        }
    }
    
    public boolean isAccountRegistered(String username, String password)
    {
        File f = new File(accountFile, username + ".dat");
        return f.exists();
    }
    
    public Account getRegisteredAccount(String username, String password) throws IOException
    {
        File f = new File(accountFile, username + ".dat");
        if (!f.exists())
            return null;
        
        Account acc = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(f)))
        {
            acc = (Account) in.readObject();
        }
        catch (ClassNotFoundException ex)
        {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return acc;
    }
    
    public Account registerNewAccount(int connectionID, String username, String password, SheetModel model) throws IOException
    {
        File f = new File(accountFile, username + ".dat");
        Account acc = new Account(connectionID, username, password, model);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f)))
        {
            out.writeObject(acc);
        }
        return acc;
    }
    
    public void updateAccountSheet(Account acc) throws IOException
    {
        File f = new File(accountFile, acc.getUsername() + ".dat");
        acc.setPlayerSheet(acc.getPlayerSheet());
        
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f)))
        {
            out.writeObject(acc);
        }
    }

    public boolean isAccountActive(String username, String password)
    {
        for (Account acc : activeAccounts.values())
        {
            if (acc.getUsername().equals(username) && acc.getPassword().equals(password))
                return true;
        }
        return false;
    }
    
    public Account getActiveAccount(int sessionID)
    {
        return activeAccounts.get(sessionID);
    }

    public void setAccountActive(int sessionID, Account acc, boolean active)
    {
        if (active)
            activeAccounts.put(sessionID, acc);
        else
            activeAccounts.remove(sessionID, acc);
    }

}
