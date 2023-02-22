/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ubs.webterminal;

import java.io.IOException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.sshd.common.forward.DefaultForwarderFactory;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.forward.AcceptAllForwardingFilter;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.shell.InvertedShell;
import org.apache.sshd.server.shell.InvertedShellWrapper;
import org.apache.sshd.server.shell.ProcessShellFactory;
import org.slf4j.LoggerFactory;

/**
 *
 * @author thiya
 */
public class App {
    private static final org.slf4j.Logger logger =  LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        try {
            SshServer sshServer=SshServer.setUpDefaultServer();
            sshServer.setHost("0.0.0.0");
            sshServer.setPort(10022);
            //sshServer.
            sshServer.setKeyPairProvider(new SimpleGeneratorHostKeyProvider());
            sshServer.setPasswordAuthenticator((username,password,session)->{
                return true;
            });
            
            
            sshServer.setForwardingFilter(AcceptAllForwardingFilter.INSTANCE);
            sshServer.setForwarderFactory(new DefaultForwarderFactory());              
            //sshServer.setShellFactory(new ProcessShellFactory("cd /tmp","/bin/sh","-i","-l"));
            sshServer.setShellFactory(new InvertedShellWrapper(new InvertedShell));
            //sshServer.setCommandFactory(new ScpCommandFactory());
            
            sshServer.start();
        } catch (IOException ex) {
           logger.error(ExceptionUtils.getStackTrace(ex));
        }

    }
    
}
