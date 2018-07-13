package de.fusion.deluxestats;

import de.fusion.deluxestats.config.ConfigManager;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

/**
 * This is the main class initializing everything
 */

public class DeluxeStats extends Plugin {

  private ExecutorService executorService = Executors.newCachedThreadPool();
  private static DeluxeStats instance;
  private static ConfigManager configuration;
  private boolean running;


  /**
   * Used to initialize the instance of the class
   */
  @Override
  public void onLoad() {
    super.onLoad();
    instance = this;
    running = true;
  }

  /**
   * Used to manage the configuration and everything else necessary.
   */
  public void onEnable() {
    super.onEnable();
    File config = new File(getDataFolder(), "config.yml");
    if (!getDataFolder().exists()) {
      getDataFolder().mkdir();
    }
    if (!config.exists()) {
      try (InputStream in = getResourceAsStream("config.yml")) {
        Files.copy(in, config.toPath());
      } catch (IOException ignored) {
      }
    }

    configuration = new ConfigManager(config).build();
    log("Loaded DeluxeStats by FusionCoding");

    PluginManager pm = ProxyServer.getInstance().getPluginManager();


  }

  /**
   * Used to disable all running tasks listening for running
   */
  @Override
  public void onDisable() {
    super.onDisable();
    running = false;
  }

  /**
   * This is used to log something to the console Automatically adds the prefix
   *
   * @param message to print
   */
  public void log(String message) {
    ProxyServer.getInstance().getConsole().sendMessage(getPrefix() + message);
  }

  /**
   * Method is used to easily run asynchronous tasks
   *
   * @return ExecutorService
   */
  public ExecutorService getMainExecutorService() {
    return executorService;
  }

  /**
   * This Method returns the instance of the plugin
   *
   * @return DeluxeStats
   */
  public static DeluxeStats getInstance() {
    return instance;
  }

  /**
   * Method will return a instance of 'ConfigManager' This allows easy access to the configuration
   * file
   *
   * @return ConfigManager
   */
  public static ConfigManager getConfiguration() {
    return configuration;
  }

  /**
   * Method will return the String of the plugin given in the configuration file
   *
   * @return String of DeluxeStats
   */
  public static String getPrefix() {
    return getConfiguration().getPath("General.Prefix").getString();
  }

  /**
   * Method returns the current state of the active plugin
   *
   * @return boolean
   */
  public boolean isRunning() {
    return running;
  }
}
