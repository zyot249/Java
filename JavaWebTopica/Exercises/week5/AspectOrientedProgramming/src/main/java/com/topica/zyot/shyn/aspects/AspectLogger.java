package com.topica.zyot.shyn.aspects;

import com.topica.zyot.shyn.exceptions.NoLoginException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

@Aspect
@Component
public class AspectLogger {
    private static final Logger logger = Logger.getLogger(AspectLogger.class.getSimpleName());

    @Around("execution(* com.topica.zyot.shyn.service..*(..))")
    public Object logMethodInfos(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object value;
        value = proceedingJoinPoint.proceed();
        long duration = System.currentTimeMillis() - start;
        logger.info(() -> new StringBuilder()
                .append(proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName())
                .append(".")
                .append(proceedingJoinPoint.getSignature().getName())
                .append(" took ")
                .append(duration).append(" ms").toString());

        CodeSignature codeSignature = (CodeSignature) proceedingJoinPoint.getSignature();
        String[] paramNames = codeSignature.getParameterNames();
        Object[] args = proceedingJoinPoint.getArgs();
        logger.info("Inputs: ");
        for (int i = 0; i < args.length; i++) {
            String input = new StringBuilder()
                    .append("Parameter #")
                    .append(i + 1)
                    .append(" : ")
                    .append(args[i].getClass().getSimpleName())
                    .append(" ")
                    .append(paramNames[i])
                    .append(" ")
                    .append(args[i].toString())
                    .toString();
            logger.info(input);
        }
        logger.info(() -> new StringBuilder()
                .append("Output: ")
                .append(value.getClass().getSimpleName())
                .append(" ")
                .append(value).toString());
        return value;
    }

    @Before("execution(* com.topica.zyot.shyn.service..*(..))")
    public void requestAdmin(JoinPoint joinPoint) throws NoLoginException {
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        try {
            logger.info("Enter username: ");
            String username = reader.readLine();
            if (username.equals("admin")) {
                logger.info("Enter password: ");
                String passwod = reader.readLine();
                if (passwod.equals("123")) {
                    logger.info("login successfully");
                } else {
                    throw new NoLoginException();
                }
            } else {
                throw new NoLoginException();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
