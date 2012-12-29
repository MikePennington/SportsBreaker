-- phpMyAdmin SQL Dump
-- version 2.10.0.2
-- http://www.phpmyadmin.net
-- 
-- Host: localhost
-- Generation Time: Jan 21, 2008 at 05:19 PM
-- Server version: 5.0.27
-- PHP Version: 4.4.4

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

-- 
-- Database: `n8vid2_benchd`
-- 

-- --------------------------------------------------------

-- 
-- Table structure for table `categories`
-- 

CREATE TABLE `categories` (
  `category_id` int(10) unsigned NOT NULL auto_increment,
  `category_name` varchar(255) NOT NULL default '',
  `active` int(10) unsigned NOT NULL default '0',
  `parent_category` int(10) unsigned NOT NULL default '0',
  `sort_order` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=22 ;

-- 
-- Dumping data for table `categories`
-- 

INSERT INTO `categories` VALUES (1, 'National', 1, 0, 1);
INSERT INTO `categories` VALUES (2, 'NFL', 1, 1, 2);
INSERT INTO `categories` VALUES (3, 'MLB', 1, 1, 3);
INSERT INTO `categories` VALUES (4, 'NBA', 1, 1, 4);
INSERT INTO `categories` VALUES (5, 'NHL', 1, 1, 5);
INSERT INTO `categories` VALUES (6, 'College FB', 1, 1, 6);
INSERT INTO `categories` VALUES (7, 'Men''s College BB', 1, 1, 7);
INSERT INTO `categories` VALUES (8, 'Women''s College BB', 1, 1, 8);
INSERT INTO `categories` VALUES (9, 'NASCAR', 1, 1, 9);
INSERT INTO `categories` VALUES (10, 'Golf', 1, 1, 10);
INSERT INTO `categories` VALUES (11, 'MLS', 1, 1, 11);
INSERT INTO `categories` VALUES (12, 'Tennis', 1, 1, 12);
INSERT INTO `categories` VALUES (13, 'Boxing', 1, 1, 13);
INSERT INTO `categories` VALUES (14, 'WNBA', 1, 1, 14);
INSERT INTO `categories` VALUES (15, 'AFL', 1, 1, 15);
INSERT INTO `categories` VALUES (16, 'World Soccer', 1, 1, 16);
INSERT INTO `categories` VALUES (17, 'Olympic Sports', 1, 1, 17);
INSERT INTO `categories` VALUES (18, 'Horse Racing', 1, 1, 18);
INSERT INTO `categories` VALUES (19, 'College Baseball', 1, 1, 19);
INSERT INTO `categories` VALUES (20, 'College Softball', 1, 1, 20);
INSERT INTO `categories` VALUES (21, 'College Hockey', 1, 1, 21);

-- --------------------------------------------------------

-- 
-- Table structure for table `news_story_stat_ids`
-- 

CREATE TABLE `news_story_stat_ids` (
  `stat_id` int(10) unsigned NOT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY  (`stat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 
-- Dumping data for table `news_story_stat_ids`
-- 

INSERT INTO `news_story_stat_ids` VALUES (1, 'View');
INSERT INTO `news_story_stat_ids` VALUES (2, 'Vote Thumbs Up');
INSERT INTO `news_story_stat_ids` VALUES (3, 'Vote Thumbs Down');

-- --------------------------------------------------------

-- 
-- Table structure for table `rss_channels`
-- 

CREATE TABLE `rss_channels` (
  `channel_id` int(10) unsigned NOT NULL auto_increment,
  `link` varchar(255) NOT NULL default '',
  `title` varchar(255) default NULL,
  `description` text,
  `language` varchar(255) default NULL,
  `copyright` varchar(255) default NULL,
  `active` int(10) unsigned NOT NULL default '1',
  `category_id` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`channel_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=54 ;

-- 
-- Dumping data for table `rss_channels`
-- 

INSERT INTO `rss_channels` VALUES (1, 'http://sports.yahoo.com/top/rss.xml', 'Yahoo! Sports - Top News', 'Latest news and information from the world of sports.', 'en-us', 'Copyright (c) 2008 Yahoo!, Inc. All rights reserved.', 1, 0);
INSERT INTO `rss_channels` VALUES (2, 'http://sports.yahoo.com/mlb/rss.xml', 'Yahoo! Sports - MLB News', 'Latest news and information about the MLB.', 'en-us', 'Copyright (c) 2008 Yahoo!, Inc. All rights reserved.', 1, 3);
INSERT INTO `rss_channels` VALUES (3, 'http://sports.yahoo.com/nfl/rss.xml', 'Yahoo! Sports - NFL News', 'Latest news and information about the NFL.', 'en-us', 'Copyright (c) 2008 Yahoo!, Inc. All rights reserved.', 1, 2);
INSERT INTO `rss_channels` VALUES (4, 'http://sports.yahoo.com/nba/rss.xml', 'Yahoo! Sports - NBA News', 'Latest news and information about the NBA.', 'en-us', 'Copyright (c) 2008 Yahoo!, Inc. All rights reserved.', 1, 4);
INSERT INTO `rss_channels` VALUES (5, 'http://sports.yahoo.com/nhl/rss.xml', 'Yahoo! Sports - NHL News', 'Latest news and information about the NHL.', 'en-us', 'Copyright (c) 2008 Yahoo!, Inc. All rights reserved.', 1, 5);
INSERT INTO `rss_channels` VALUES (6, 'http://sports.espn.go.com/espn/rss/news', 'ESPN.com', 'Latest news from ESPN.com', 'en-us', NULL, 1, 0);
INSERT INTO `rss_channels` VALUES (7, 'http://sports.espn.go.com/espn/rss/nfl/news', 'ESPN.com - NFL', 'Latest NFL news from ESPN.com', 'en-us', NULL, 1, 2);
INSERT INTO `rss_channels` VALUES (8, 'http://sports.espn.go.com/espn/rss/nba/news', 'ESPN.com - NBA', 'Latest NBA news from ESPN.com', 'en-us', NULL, 1, 4);
INSERT INTO `rss_channels` VALUES (9, 'http://sports.espn.go.com/espn/rss/mlb/news', 'ESPN.com - MLB', 'Latest MLB news from ESPN.com', 'en-us', NULL, 1, 3);
INSERT INTO `rss_channels` VALUES (10, 'http://feeds.feedburner.com/espn/nhl?format=xml', 'ESPN.com - NHL', 'Latest NHL news from ESPN.com', 'en-us', NULL, 1, 5);
INSERT INTO `rss_channels` VALUES (11, 'http://sports.espn.go.com/espn/rss/golf/news', 'ESPN.com - Golf', 'Latest Golf news from ESPN.com', 'en-us', NULL, 1, 10);
INSERT INTO `rss_channels` VALUES (12, 'http://sports.espn.go.com/espn/rss/rpm/news', 'ESPN.com - Autos', 'Latest Autos news from ESPN.com', 'en-us', NULL, 1, 9);
INSERT INTO `rss_channels` VALUES (13, 'http://sports.espn.go.com/espn/rss/tennis/news', 'ESPN.com - Tennis', 'Latest Tennis news from ESPN.com', 'en-us', NULL, 1, 12);
INSERT INTO `rss_channels` VALUES (14, 'http://sports.espn.go.com/espn/rss/boxing/news', 'ESPN.com - Boxing', 'Latest Boxing news from ESPN.com', 'en-us', NULL, 1, 13);
INSERT INTO `rss_channels` VALUES (15, 'http://soccernet.espn.go.com/rss/news', 'ESPNsoccernet ', 'Latest Soccer news from ESPNsoccernet', 'en-us', NULL, 1, 16);
INSERT INTO `rss_channels` VALUES (16, 'http://sports.espn.go.com/espn/rss/ncb/news', 'ESPN.com - Men''s College Basketball', 'Latest Men''s College Basketball news from ESPN.com', 'en-us', NULL, 1, 7);
INSERT INTO `rss_channels` VALUES (17, 'http://sports.espn.go.com/espn/rss/ncf/news', 'ESPN.com - College Football', 'Latest College Football news from ESPN.com', 'en-us', NULL, 1, 6);
INSERT INTO `rss_channels` VALUES (18, 'http://sports.espn.go.com/espn/rss/ncaa/news', 'ESPN.com - College', 'Latest College  news from ESPN.com', 'en-us', NULL, 1, 0);
INSERT INTO `rss_channels` VALUES (19, 'http://sports.espn.go.com/espn/rss/outdoors/news', 'ESPN.com - OUTDOORS', 'Latest Outdoors news from ESPN.com', 'en-us', NULL, 1, 0);
INSERT INTO `rss_channels` VALUES (20, 'http://sports.espn.go.com/espn/rss/bassmaster/news', 'ESPN.com - BASSMASTER', 'Latest Bassmaster news from ESPN.com', 'en-us', NULL, 1, 0);
INSERT INTO `rss_channels` VALUES (21, 'http://sports.espn.go.com/espn/rss/oly/news', 'ESPN.com - Olympic Sports', 'Latest Olympic Sports news from ESPN.com', 'en-us', NULL, 1, 17);
INSERT INTO `rss_channels` VALUES (22, 'http://sports.espn.go.com/espn/rss/horse/news', 'ESPN.com - Horse Racing', 'Latest Horse Racing news from ESPN.com', 'en-us', NULL, 1, 18);
INSERT INTO `rss_channels` VALUES (23, 'http://sports.yahoo.com/nascar/rss.xml', 'Yahoo! Sports - NASCAR News', 'Latest news and information about the NASCAR.', 'en-us', 'Copyright (c) 2008 Yahoo!, Inc. All rights reserved.', 1, 9);
INSERT INTO `rss_channels` VALUES (24, 'http://sports.yahoo.com/golf/rss.xml', 'Yahoo! Sports - Golf News', 'Latest news and information about the Golf.', 'en-us', 'Copyright (c) 2008 Yahoo!, Inc. All rights reserved.', 1, 10);
INSERT INTO `rss_channels` VALUES (25, 'http://sports.yahoo.com/ncaab/rss.xml', 'Yahoo! Sports - NCAA Men''s Hoops News', 'Latest news and information about the NCAA Men''s Hoops.', 'en-us', 'Copyright (c) 2008 Yahoo!, Inc. All rights reserved.', 1, 7);
INSERT INTO `rss_channels` VALUES (26, 'http://sports.yahoo.com/ncaaw/rss.xml', 'Yahoo! Sports - NCAA Women''s Hoops News', 'Latest news and information about the NCAA Women''s Hoops.', 'en-us', 'Copyright (c) 2008 Yahoo!, Inc. All rights reserved.', 1, 8);
INSERT INTO `rss_channels` VALUES (27, 'http://sports.yahoo.com/ten/rss.xml', 'Yahoo! Sports - Tennis News', 'Latest news and information about the Tennis.', 'en-us', 'Copyright (c) 2008 Yahoo!, Inc. All rights reserved.', 1, 12);
INSERT INTO `rss_channels` VALUES (28, 'http://sports.yahoo.com/ncaabb/rss.xml', 'Yahoo! Sports - NCAA Baseball News', 'Latest news and information about the NCAA Baseball.', 'en-us', 'Copyright (c) 2008 Yahoo!, Inc. All rights reserved.', 1, 19);
INSERT INTO `rss_channels` VALUES (29, 'http://sports.yahoo.com/wnba/rss.xml', 'Yahoo! Sports - WNBA News', 'Latest news and information about the WNBA.', 'en-us', 'Copyright (c) 2008 Yahoo!, Inc. All rights reserved.', 1, 14);
INSERT INTO `rss_channels` VALUES (30, 'http://sports.yahoo.com/ncaaf/rss.xml', 'Yahoo! Sports - NCAA Football News', 'Latest news and information about the NCAA Football.', 'en-us', 'Copyright (c) 2008 Yahoo!, Inc. All rights reserved.', 1, 6);
INSERT INTO `rss_channels` VALUES (31, 'http://sports.yahoo.com/ncaah/rss.xml', 'Yahoo! Sports - NCAA Hockey News', 'Latest news and information about the NCAA Hockey.', 'en-us', 'Copyright (c) 2008 Yahoo!, Inc. All rights reserved.', 1, 21);
INSERT INTO `rss_channels` VALUES (32, 'http://sports.yahoo.com/olympics/rss.xml', 'Yahoo! Sports - Olympics News', 'Latest news and information about the Olympics.', 'en-us', 'Copyright (c) 2008 Yahoo!, Inc. All rights reserved.', 1, 17);
INSERT INTO `rss_channels` VALUES (33, 'http://sports.yahoo.com/cart/rss.xml', 'Yahoo! Sports - Champ Car News', 'Latest news and information about the Champ Car.', 'en-us', 'Copyright (c) 2008 Yahoo!, Inc. All rights reserved.', 1, 0);
INSERT INTO `rss_channels` VALUES (34, 'http://sports.yahoo.com/irl/rss.xml', 'Yahoo! Sports - IRL News', 'Latest news and information about the IRL.', 'en-us', 'Copyright (c) 2008 Yahoo!, Inc. All rights reserved.', 1, 0);
INSERT INTO `rss_channels` VALUES (35, 'http://sports.yahoo.com/sow/rss.xml', 'Yahoo! Sports - World Soccer News', 'Latest news and information about the World Soccer.', 'en-us', 'Copyright (c) 2008 Yahoo!, Inc. All rights reserved.', 1, 16);
INSERT INTO `rss_channels` VALUES (36, 'http://sports.yahoo.com/mls/rss.xml', 'Yahoo! Sports - MLS News', 'Latest news and information about the MLS.', 'en-us', 'Copyright (c) 2008 Yahoo!, Inc. All rights reserved.', 1, 11);
INSERT INTO `rss_channels` VALUES (37, 'http://sports.yahoo.com/ski/rss.xml', 'Yahoo! Sports - World Cup Skiing News', 'Latest news and information about the World Cup Skiing.', 'en-us', 'Copyright (c) 2008 Yahoo!, Inc. All rights reserved.', 1, 0);
INSERT INTO `rss_channels` VALUES (38, 'http://sports.yahoo.com/sc/rss.xml', 'Yahoo! Sports - Cycling News', 'Latest news and information about the Cycling.', 'en-us', 'Copyright (c) 2008 Yahoo!, Inc. All rights reserved.', 1, 0);
INSERT INTO `rss_channels` VALUES (39, 'http://sports.yahoo.com/rah/rss.xml', 'Yahoo! Sports - Horse Racing News', 'Latest news and information about the Horse Racing.', 'en-us', 'Copyright (c) 2008 Yahoo!, Inc. All rights reserved.', 1, 18);
INSERT INTO `rss_channels` VALUES (40, 'http://feeds.feedburner.com/foxsports/RSS/headlines', 'FOXSports.com News', 'FOXSports.com Top Ranked Headlines Syndication Feeds', 'en-us', NULL, 1, 0);
INSERT INTO `rss_channels` VALUES (41, 'http://feeds.feedburner.com/Foxsports/rss/MLB', 'FOXSports.com News for MLB', 'FOXSports.com Top Ranked Headlines Syndication Feeds for MLB', 'en-us', NULL, 1, 3);
INSERT INTO `rss_channels` VALUES (42, 'http://feeds.feedburner.com/foxsports/RSS/NFL', 'FOXSports.com News for NFL', 'FOXSports.com Top Ranked Headlines Syndication Feeds for NFL', 'en-us', NULL, 1, 2);
INSERT INTO `rss_channels` VALUES (43, 'http://feeds.feedburner.com/foxsports/RSS/cfb', 'FOXSports.com News for COLLEGE FOOTBALL', 'FOXSports.com Top Ranked Headlines Syndication Feeds for COLLEGE FOOTBALL', 'en-us', NULL, 1, 6);
INSERT INTO `rss_channels` VALUES (44, 'http://feeds.feedburner.com/foxsports/RSS/nba', 'FOXSports.com News for NBA', 'FOXSports.com Top Ranked Headlines Syndication Feeds for NBA', 'en-us', NULL, 1, 4);
INSERT INTO `rss_channels` VALUES (45, 'http://feeds.feedburner.com/foxsports/RSS/nhl', 'FOXSports.com News for NHL', 'FOXSports.com Top Ranked Headlines Syndication Feeds for NHL', 'en-us', NULL, 1, 5);
INSERT INTO `rss_channels` VALUES (46, 'http://feeds.feedburner.com/foxsports/RSS/cbk', 'FOXSports.com News for COLLEGE BASKETBALL', 'FOXSports.com Top Ranked Headlines Syndication Feeds for COLLEGE BASKETBALL', 'en-us', NULL, 1, 7);
INSERT INTO `rss_channels` VALUES (47, 'http://feeds.feedburner.com/foxsports/RSS/NASCAR', 'FOXSports.com News for NASCAR', 'FOXSports.com Top Ranked Headlines Syndication Feeds for NASCAR', 'en-us', NULL, 1, 9);
INSERT INTO `rss_channels` VALUES (48, 'http://feeds.feedburner.com/foxsports/RSS/golf', 'FOXSports.com News for Golf', 'FOXSports.com Top Ranked Headlines Syndication Feeds for Golf', 'en-us', NULL, 1, 10);
INSERT INTO `rss_channels` VALUES (49, 'http://feeds.feedburner.com/foxsports/RSS/soccer', 'FOXSports.com News for FOX SOCCER', 'FOXSports.com Top Ranked Headlines Syndication Feeds for FOX SOCCER', 'en-us', NULL, 1, 16);
INSERT INTO `rss_channels` VALUES (50, 'http://msn.foxsports.com/feedout/syndicatedContent?categoryId=235', 'FOXSports.com News for Olympics', 'FOXSports.com Top Ranked Headlines Syndication Feeds for Olympics', 'en-us', NULL, 1, 17);
INSERT INTO `rss_channels` VALUES (51, 'http://feeds.feedburner.com/foxsports/RSS/tennis', 'FOXSports.com News for TENNIS', 'FOXSports.com Top Ranked Headlines Syndication Feeds for TENNIS', 'en-us', NULL, 1, 12);
INSERT INTO `rss_channels` VALUES (52, 'http://feeds.feedburner.com/foxsports/RSS/horseracing', 'FOXSports.com News for Horseracing', 'FOXSports.com Top Ranked Headlines Syndication Feeds for Horseracing', 'en-us', NULL, 1, 18);
INSERT INTO `rss_channels` VALUES (53, 'http://feeds.feedburner.com/foxsports/RSS/wnba', 'FOXSports.com News for WNBA', 'FOXSports.com Top Ranked Headlines Syndication Feeds for WNBA', 'en-us', NULL, 1, 14);

-- --------------------------------------------------------

-- 
-- Table structure for table `rss_channel_categories`
-- 

CREATE TABLE `rss_channel_categories` (
  `channel_id` int(10) unsigned NOT NULL auto_increment,
  `category_id` int(10) unsigned NOT NULL,
  `primary` int(10) unsigned NOT NULL default '1',
  PRIMARY KEY  (`channel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=23 ;

-- 
-- Dumping data for table `rss_channel_categories`
-- 

INSERT INTO `rss_channel_categories` VALUES (1, 1, 1);
INSERT INTO `rss_channel_categories` VALUES (2, 3, 1);
INSERT INTO `rss_channel_categories` VALUES (3, 2, 1);
INSERT INTO `rss_channel_categories` VALUES (4, 4, 1);
INSERT INTO `rss_channel_categories` VALUES (5, 5, 1);
INSERT INTO `rss_channel_categories` VALUES (6, 1, 1);
INSERT INTO `rss_channel_categories` VALUES (7, 2, 1);
INSERT INTO `rss_channel_categories` VALUES (8, 4, 1);
INSERT INTO `rss_channel_categories` VALUES (9, 3, 1);
INSERT INTO `rss_channel_categories` VALUES (10, 5, 1);
INSERT INTO `rss_channel_categories` VALUES (11, 10, 1);
INSERT INTO `rss_channel_categories` VALUES (12, 9, 1);
INSERT INTO `rss_channel_categories` VALUES (13, 12, 1);
INSERT INTO `rss_channel_categories` VALUES (14, 13, 1);
INSERT INTO `rss_channel_categories` VALUES (15, 16, 1);
INSERT INTO `rss_channel_categories` VALUES (16, 7, 1);
INSERT INTO `rss_channel_categories` VALUES (17, 6, 1);
INSERT INTO `rss_channel_categories` VALUES (21, 17, 1);
INSERT INTO `rss_channel_categories` VALUES (22, 18, 1);

-- --------------------------------------------------------

-- 
-- Table structure for table `rss_channel_images`
-- 

CREATE TABLE `rss_channel_images` (
  `image_id` int(10) unsigned NOT NULL auto_increment,
  `url` varchar(255) NOT NULL default '',
  `link` varchar(255) NOT NULL default '',
  `width` int(10) unsigned NOT NULL default '0',
  `height` int(10) unsigned NOT NULL default '0',
  `channel_id` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`image_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=54 ;

-- 
-- Dumping data for table `rss_channel_images`
-- 

INSERT INTO `rss_channel_images` VALUES (1, 'http://us.i1.yimg.com/us.yimg.com/i/us/sp/b/ysp_logo_rss.gif', 'http://sports.yahoo.com', 126, 15, 1);
INSERT INTO `rss_channel_images` VALUES (2, 'http://us.i1.yimg.com/us.yimg.com/i/us/sp/b/ysp_logo_rss.gif', 'http://sports.yahoo.com', 126, 15, 2);
INSERT INTO `rss_channel_images` VALUES (3, 'http://us.i1.yimg.com/us.yimg.com/i/us/sp/b/ysp_logo_rss.gif', 'http://sports.yahoo.com', 126, 15, 3);
INSERT INTO `rss_channel_images` VALUES (4, 'http://us.i1.yimg.com/us.yimg.com/i/us/sp/b/ysp_logo_rss.gif', 'http://sports.yahoo.com', 126, 15, 4);
INSERT INTO `rss_channel_images` VALUES (5, 'http://us.i1.yimg.com/us.yimg.com/i/us/sp/b/ysp_logo_rss.gif', 'http://sports.yahoo.com', 126, 15, 5);
INSERT INTO `rss_channel_images` VALUES (6, 'http://assets.espn.go.com/i/tvlistings/tv_espn_original.gif', 'http://espn.go.com', 84, 34, 6);
INSERT INTO `rss_channel_images` VALUES (7, 'http://assets.espn.go.com/i/tvlistings/tv_espn_original.gif', 'http://espn.go.com', 84, 34, 7);
INSERT INTO `rss_channel_images` VALUES (8, 'http://assets.espn.go.com/i/tvlistings/tv_espn_original.gif', 'http://espn.go.com', 84, 34, 8);
INSERT INTO `rss_channel_images` VALUES (9, 'http://assets.espn.go.com/i/tvlistings/tv_espn_original.gif', 'http://espn.go.com', 84, 34, 9);
INSERT INTO `rss_channel_images` VALUES (10, 'http://assets.espn.go.com/i/tvlistings/tv_espn_original.gif', 'http://www.espn.com', 0, 0, 10);
INSERT INTO `rss_channel_images` VALUES (11, 'http://assets.espn.go.com/i/tvlistings/tv_espn_original.gif', 'http://espn.go.com', 84, 34, 11);
INSERT INTO `rss_channel_images` VALUES (12, 'http://assets.espn.go.com/i/tvlistings/tv_espn_original.gif', 'http://espn.go.com', 84, 34, 12);
INSERT INTO `rss_channel_images` VALUES (13, 'http://assets.espn.go.com/i/tvlistings/tv_espn_original.gif', 'http://espn.go.com', 84, 34, 13);
INSERT INTO `rss_channel_images` VALUES (14, 'http://assets.espn.go.com/i/tvlistings/tv_espn_original.gif', 'http://espn.go.com', 84, 34, 14);
INSERT INTO `rss_channel_images` VALUES (15, 'http://ak-sports.espn.go.com/f/1917/8668/6H/espn.go.com/i/tvlistings/tv_espn_original.gif', 'http://soccernet.espn.go.com', 84, 34, 15);
INSERT INTO `rss_channel_images` VALUES (16, 'http://assets.espn.go.com/i/tvlistings/tv_espn_original.gif', 'http://espn.go.com', 84, 34, 16);
INSERT INTO `rss_channel_images` VALUES (17, 'http://assets.espn.go.com/i/tvlistings/tv_espn_original.gif', 'http://espn.go.com', 84, 34, 17);
INSERT INTO `rss_channel_images` VALUES (18, 'http://assets.espn.go.com/i/tvlistings/tv_espn_original.gif', 'http://espn.go.com', 84, 34, 18);
INSERT INTO `rss_channel_images` VALUES (19, 'http://assets.espn.go.com/i/tvlistings/tv_espn_original.gif', 'http://espn.go.com', 84, 34, 19);
INSERT INTO `rss_channel_images` VALUES (20, 'http://assets.espn.go.com/i/tvlistings/tv_espn_original.gif', 'http://espn.go.com', 84, 34, 20);
INSERT INTO `rss_channel_images` VALUES (21, 'http://assets.espn.go.com/i/tvlistings/tv_espn_original.gif', 'http://espn.go.com', 84, 34, 21);
INSERT INTO `rss_channel_images` VALUES (22, 'http://assets.espn.go.com/i/tvlistings/tv_espn_original.gif', 'http://espn.go.com', 84, 34, 22);
INSERT INTO `rss_channel_images` VALUES (23, 'http://us.i1.yimg.com/us.yimg.com/i/us/sp/b/ysp_logo_rss.gif', 'http://sports.yahoo.com', 126, 15, 23);
INSERT INTO `rss_channel_images` VALUES (24, 'http://us.i1.yimg.com/us.yimg.com/i/us/sp/b/ysp_logo_rss.gif', 'http://sports.yahoo.com', 126, 15, 24);
INSERT INTO `rss_channel_images` VALUES (25, 'http://us.i1.yimg.com/us.yimg.com/i/us/sp/b/ysp_logo_rss.gif', 'http://sports.yahoo.com', 126, 15, 25);
INSERT INTO `rss_channel_images` VALUES (26, 'http://us.i1.yimg.com/us.yimg.com/i/us/sp/b/ysp_logo_rss.gif', 'http://sports.yahoo.com', 126, 15, 26);
INSERT INTO `rss_channel_images` VALUES (27, 'http://us.i1.yimg.com/us.yimg.com/i/us/sp/b/ysp_logo_rss.gif', 'http://sports.yahoo.com', 126, 15, 27);
INSERT INTO `rss_channel_images` VALUES (28, 'http://us.i1.yimg.com/us.yimg.com/i/us/sp/b/ysp_logo_rss.gif', 'http://sports.yahoo.com', 126, 15, 28);
INSERT INTO `rss_channel_images` VALUES (29, 'http://us.i1.yimg.com/us.yimg.com/i/us/sp/b/ysp_logo_rss.gif', 'http://sports.yahoo.com', 126, 15, 29);
INSERT INTO `rss_channel_images` VALUES (30, 'http://us.i1.yimg.com/us.yimg.com/i/us/sp/b/ysp_logo_rss.gif', 'http://sports.yahoo.com', 126, 15, 30);
INSERT INTO `rss_channel_images` VALUES (31, 'http://us.i1.yimg.com/us.yimg.com/i/us/sp/b/ysp_logo_rss.gif', 'http://sports.yahoo.com', 126, 15, 31);
INSERT INTO `rss_channel_images` VALUES (32, 'http://us.i1.yimg.com/us.yimg.com/i/us/sp/b/ysp_logo_rss.gif', 'http://sports.yahoo.com', 126, 15, 32);
INSERT INTO `rss_channel_images` VALUES (33, 'http://us.i1.yimg.com/us.yimg.com/i/us/sp/b/ysp_logo_rss.gif', 'http://sports.yahoo.com', 126, 15, 33);
INSERT INTO `rss_channel_images` VALUES (34, 'http://us.i1.yimg.com/us.yimg.com/i/us/sp/b/ysp_logo_rss.gif', 'http://sports.yahoo.com', 126, 15, 34);
INSERT INTO `rss_channel_images` VALUES (35, 'http://us.i1.yimg.com/us.yimg.com/i/us/sp/b/ysp_logo_rss.gif', 'http://sports.yahoo.com', 126, 15, 35);
INSERT INTO `rss_channel_images` VALUES (36, 'http://us.i1.yimg.com/us.yimg.com/i/us/sp/b/ysp_logo_rss.gif', 'http://sports.yahoo.com', 126, 15, 36);
INSERT INTO `rss_channel_images` VALUES (37, 'http://us.i1.yimg.com/us.yimg.com/i/us/sp/b/ysp_logo_rss.gif', 'http://sports.yahoo.com', 126, 15, 37);
INSERT INTO `rss_channel_images` VALUES (38, 'http://us.i1.yimg.com/us.yimg.com/i/us/sp/b/ysp_logo_rss.gif', 'http://sports.yahoo.com', 126, 15, 38);
INSERT INTO `rss_channel_images` VALUES (39, 'http://us.i1.yimg.com/us.yimg.com/i/us/sp/b/ysp_logo_rss.gif', 'http://sports.yahoo.com', 126, 15, 39);
INSERT INTO `rss_channel_images` VALUES (40, 'http://msn.foxsports.com/fe/img/foxsports_logo_52x39.jpg', 'http://msn.foxsports.com', 52, 39, 40);
INSERT INTO `rss_channel_images` VALUES (41, 'http://msn.foxsports.com/fe/img/foxsports_logo_52x39.jpg', 'http://msn.foxsports.com/mlb', 52, 39, 41);
INSERT INTO `rss_channel_images` VALUES (42, 'http://msn.foxsports.com/fe/img/foxsports_logo_52x39.jpg', 'http://msn.foxsports.com/nfl', 52, 39, 42);
INSERT INTO `rss_channel_images` VALUES (43, 'http://msn.foxsports.com/fe/img/foxsports_logo_52x39.jpg', 'http://msn.foxsports.com/cfb', 52, 39, 43);
INSERT INTO `rss_channel_images` VALUES (44, 'http://msn.foxsports.com/fe/img/foxsports_logo_52x39.jpg', 'http://msn.foxsports.com/nba', 52, 39, 44);
INSERT INTO `rss_channel_images` VALUES (45, 'http://msn.foxsports.com/fe/img/foxsports_logo_52x39.jpg', 'http://msn.foxsports.com/nhl', 52, 39, 45);
INSERT INTO `rss_channel_images` VALUES (46, 'http://msn.foxsports.com/fe/img/foxsports_logo_52x39.jpg', 'http://msn.foxsports.com/cbk', 52, 39, 46);
INSERT INTO `rss_channel_images` VALUES (47, 'http://msn.foxsports.com/fe/img/foxsports_logo_52x39.jpg', 'http://msn.foxsports.com/nascar', 52, 39, 47);
INSERT INTO `rss_channel_images` VALUES (48, 'http://msn.foxsports.com/fe/img/foxsports_logo_52x39.jpg', 'http://msn.foxsports.com/golf', 52, 39, 48);
INSERT INTO `rss_channel_images` VALUES (49, 'http://msn.foxsports.com/fe/img/foxsports_logo_52x39.jpg', 'http://msn.foxsports.com/foxsoccer', 52, 39, 49);
INSERT INTO `rss_channel_images` VALUES (50, 'http://msn.foxsports.com/fe/img/foxsports_logo_52x39.jpg', 'http://msn.foxsports.com/name/public/winterOlympics06', 52, 39, 50);
INSERT INTO `rss_channel_images` VALUES (51, 'http://msn.foxsports.com/fe/img/foxsports_logo_52x39.jpg', 'http://msn.foxsports.com/tennis', 52, 39, 51);
INSERT INTO `rss_channel_images` VALUES (52, 'http://msn.foxsports.com/fe/img/foxsports_logo_52x39.jpg', 'http://msn.foxsports.com/horseracing', 52, 39, 52);
INSERT INTO `rss_channel_images` VALUES (53, 'http://msn.foxsports.com/fe/img/foxsports_logo_52x39.jpg', 'http://msn.foxsports.com', 52, 39, 53);

-- --------------------------------------------------------

-- 
-- Table structure for table `teams`
-- 

CREATE TABLE `teams` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `category_id` int(10) unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `location` varchar(254) default NULL,
  `conference` varchar(255) NOT NULL,
  `subconference` varchar(254) default NULL,
  PRIMARY KEY  USING BTREE (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=95 ;

-- 
-- Dumping data for table `teams`
-- 

INSERT INTO `teams` VALUES (1, 2, 'Ravens', 'Baltimore', 'AFC-North', NULL);
INSERT INTO `teams` VALUES (2, 2, 'Bengals', 'Cincinnati', 'AFC-North', NULL);
INSERT INTO `teams` VALUES (3, 2, 'Browns', 'Cleveland', 'AFC-North', NULL);
INSERT INTO `teams` VALUES (4, 2, 'Steelers', 'Pittsburgh', 'AFC-North', NULL);
INSERT INTO `teams` VALUES (5, 2, 'Bears', 'Chicago', 'NFC-North', NULL);
INSERT INTO `teams` VALUES (6, 2, 'Lions', 'Detroit', 'NFC-North', NULL);
INSERT INTO `teams` VALUES (7, 2, 'Packers', 'Green Bay', 'NFC-North', NULL);
INSERT INTO `teams` VALUES (8, 2, 'Vikings', 'Minnesota', 'NFC-North', NULL);
INSERT INTO `teams` VALUES (9, 2, 'Texans', 'Houston', 'AFC-South', NULL);
INSERT INTO `teams` VALUES (10, 2, 'Colts', 'Indianapolis', 'AFC-South', NULL);
INSERT INTO `teams` VALUES (11, 2, 'Jaguars', 'Jacksonville', 'AFC-South', NULL);
INSERT INTO `teams` VALUES (12, 2, 'Titans', 'Tennessee', 'AFC-South', NULL);
INSERT INTO `teams` VALUES (13, 2, 'Falcons', 'Atlanta', 'NFC-South', NULL);
INSERT INTO `teams` VALUES (14, 2, 'Panthers', 'Carolina', 'NFC-South', NULL);
INSERT INTO `teams` VALUES (15, 2, 'Saints', 'New Orleans', 'NFC-South', NULL);
INSERT INTO `teams` VALUES (16, 2, 'Buccaneers', 'Tampa Bay', 'NFC-South', NULL);
INSERT INTO `teams` VALUES (17, 2, 'Bills', 'Buffalo', 'AFC-East', NULL);
INSERT INTO `teams` VALUES (18, 2, 'Dolphins', 'Miami', 'AFC-East', NULL);
INSERT INTO `teams` VALUES (19, 2, 'Patriots', 'New England', 'AFC-East', NULL);
INSERT INTO `teams` VALUES (20, 2, 'Jets', 'New York', 'AFC-East', NULL);
INSERT INTO `teams` VALUES (21, 2, 'Cowboys', 'Dallas', 'NFC-East', NULL);
INSERT INTO `teams` VALUES (22, 2, 'Giants', 'New York', 'NFC-East', NULL);
INSERT INTO `teams` VALUES (23, 2, 'Eagles', 'Philadelphia', 'NFC-East', NULL);
INSERT INTO `teams` VALUES (24, 2, 'Redskins', 'Washington', 'NFC-East', NULL);
INSERT INTO `teams` VALUES (25, 2, 'Broncos', 'Denver', 'AFC-West', NULL);
INSERT INTO `teams` VALUES (26, 2, 'Chiefs', 'Kansas City', 'AFC-West', NULL);
INSERT INTO `teams` VALUES (27, 2, 'Raiders', 'Oakland', 'AFC-West', NULL);
INSERT INTO `teams` VALUES (28, 2, 'Chargers', 'San Diego', 'AFC-West', NULL);
INSERT INTO `teams` VALUES (29, 2, 'Cardinals', 'Arizona', 'NFC-West', NULL);
INSERT INTO `teams` VALUES (30, 2, '49ers', 'San Francisco', 'NFC-West', NULL);
INSERT INTO `teams` VALUES (31, 2, 'Seattle Seahawks', NULL, 'NFC-West', NULL);
INSERT INTO `teams` VALUES (32, 2, 'St. Louis Rams', NULL, 'NFC-West', NULL);
INSERT INTO `teams` VALUES (33, 3, 'Baltimore Orioles', NULL, 'AL East', NULL);
INSERT INTO `teams` VALUES (34, 3, 'Boston Red Sox', NULL, 'AL East', NULL);
INSERT INTO `teams` VALUES (35, 3, 'New York Yankees', NULL, 'AL East', NULL);
INSERT INTO `teams` VALUES (36, 3, 'Tampa Bay Devil Rays', NULL, 'AL East', NULL);
INSERT INTO `teams` VALUES (37, 3, 'Toronto Blue Jays', NULL, 'AL East', NULL);
INSERT INTO `teams` VALUES (38, 3, 'Chicago White Sox', NULL, 'AL Central', NULL);
INSERT INTO `teams` VALUES (39, 3, 'Cleveland Indians', NULL, 'AL Central', NULL);
INSERT INTO `teams` VALUES (40, 3, 'Detroit Tigers', NULL, 'AL Central', NULL);
INSERT INTO `teams` VALUES (41, 3, 'Kansas City Royals', NULL, 'AL Central', NULL);
INSERT INTO `teams` VALUES (42, 3, 'Minnesota Twins', NULL, 'AL Central', NULL);
INSERT INTO `teams` VALUES (43, 3, 'Los Angeles Angels', NULL, 'AL West', NULL);
INSERT INTO `teams` VALUES (44, 3, 'Oakland Athletics', NULL, 'AL West', NULL);
INSERT INTO `teams` VALUES (45, 3, 'Seattle Mariners', NULL, 'AL West', NULL);
INSERT INTO `teams` VALUES (46, 3, 'Texas Rangers', NULL, 'AL West', NULL);
INSERT INTO `teams` VALUES (47, 3, 'Atlanta Braves', NULL, 'NL East', NULL);
INSERT INTO `teams` VALUES (48, 3, 'Florida Marlins', NULL, 'NL East', NULL);
INSERT INTO `teams` VALUES (49, 3, 'New York Mets', NULL, 'NL East', NULL);
INSERT INTO `teams` VALUES (50, 3, 'Philadelphia Phillies', NULL, 'NL East', NULL);
INSERT INTO `teams` VALUES (51, 3, 'Washington Nationals', NULL, 'NL East', NULL);
INSERT INTO `teams` VALUES (52, 3, 'Chicago Cubs', NULL, 'NL Central', NULL);
INSERT INTO `teams` VALUES (53, 3, 'Cincinnati Reds', NULL, 'NL Central', NULL);
INSERT INTO `teams` VALUES (54, 3, 'Houston Astros', NULL, 'NL Central', NULL);
INSERT INTO `teams` VALUES (55, 3, 'Milwaukee Brewers', NULL, 'NL Central', NULL);
INSERT INTO `teams` VALUES (56, 3, 'Pittsburgh Pirates', NULL, 'NL Central', NULL);
INSERT INTO `teams` VALUES (57, 3, 'St. Louis Cardinals', NULL, 'NL Central', NULL);
INSERT INTO `teams` VALUES (58, 3, 'Arizona Diamondbacks', NULL, 'NL West', NULL);
INSERT INTO `teams` VALUES (59, 3, 'Colorado Rockies', NULL, 'NL West', NULL);
INSERT INTO `teams` VALUES (60, 3, 'Los Angeles Dodgers', NULL, 'NL West', NULL);
INSERT INTO `teams` VALUES (61, 3, 'San Diego Padres', NULL, 'NL West', NULL);
INSERT INTO `teams` VALUES (62, 3, 'San Francisco Giants', NULL, 'NL West', NULL);
INSERT INTO `teams` VALUES (63, 4, 'Boston Celtics', NULL, 'Atlantic Division ', NULL);
INSERT INTO `teams` VALUES (64, 4, 'New Jersey Nets', NULL, 'Atlantic Division ', NULL);
INSERT INTO `teams` VALUES (65, 4, 'New York Knicks', NULL, 'Atlantic Division ', NULL);
INSERT INTO `teams` VALUES (66, 4, 'Philadelphia 76ers', NULL, 'Atlantic Division ', NULL);
INSERT INTO `teams` VALUES (67, 4, 'Toronto Raptors', NULL, 'Atlantic Division ', NULL);
INSERT INTO `teams` VALUES (68, 4, 'Chicago Bulls', NULL, 'Central Division ', NULL);
INSERT INTO `teams` VALUES (69, 4, 'Cleveland Cavaliers', NULL, 'Central Division ', NULL);
INSERT INTO `teams` VALUES (70, 4, 'Detroit Pistons', NULL, 'Central Division ', NULL);
INSERT INTO `teams` VALUES (71, 4, 'Indiana Pacers', NULL, 'Central Division ', NULL);
INSERT INTO `teams` VALUES (72, 4, 'Milwaukee Bucks', NULL, 'Central Division ', NULL);
INSERT INTO `teams` VALUES (73, 4, 'Atlanta Hawks', NULL, 'Southeast Division ', NULL);
INSERT INTO `teams` VALUES (74, 4, 'Charlotte Bobcats', NULL, 'Southeast Division ', NULL);
INSERT INTO `teams` VALUES (75, 4, 'Miami Heat', NULL, 'Southeast Division ', NULL);
INSERT INTO `teams` VALUES (76, 4, 'Orlando Magic', NULL, 'Southeast Division ', NULL);
INSERT INTO `teams` VALUES (77, 4, 'Washington Wizards', NULL, 'Southeast Division ', NULL);
INSERT INTO `teams` VALUES (78, 4, 'Dallas Mavericks', NULL, 'Southwest Division ', NULL);
INSERT INTO `teams` VALUES (79, 4, 'Houston Rockets', NULL, 'Southwest Division ', NULL);
INSERT INTO `teams` VALUES (80, 4, 'Memphis Grizzlies', NULL, 'Southwest Division ', NULL);
INSERT INTO `teams` VALUES (81, 4, 'New Orleans Hornets', NULL, 'Southwest Division ', NULL);
INSERT INTO `teams` VALUES (82, 4, 'San Antonio Spurs', NULL, 'Southwest Division ', NULL);
INSERT INTO `teams` VALUES (83, 4, 'Denver Nuggets', NULL, 'Northwest Division', NULL);
INSERT INTO `teams` VALUES (84, 4, 'Minnesota Timberwolves', NULL, 'Northwest Division', NULL);
INSERT INTO `teams` VALUES (85, 4, 'Portland Trail Blazers', NULL, 'Northwest Division', NULL);
INSERT INTO `teams` VALUES (86, 4, 'Seattle SuperSonics', NULL, 'Northwest Division', NULL);
INSERT INTO `teams` VALUES (87, 4, 'Utah Jazz', NULL, 'Northwest Division', NULL);
INSERT INTO `teams` VALUES (88, 4, 'Golden State Warriors', NULL, 'Pacific Division ', NULL);
INSERT INTO `teams` VALUES (89, 4, 'Los Angeles Clippers', NULL, 'Pacific Division ', NULL);
INSERT INTO `teams` VALUES (90, 4, 'Los Angeles Lakers', NULL, 'Pacific Division ', NULL);
INSERT INTO `teams` VALUES (91, 4, 'Phoenix Suns', NULL, 'Pacific Division ', NULL);
INSERT INTO `teams` VALUES (92, 4, 'Sacramento Kings', NULL, 'Pacific Division ', NULL);

-- --------------------------------------------------------

-- 
-- Table structure for table `user_property_ids`
-- 

CREATE TABLE `user_property_ids` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- 
-- Dumping data for table `user_property_ids`
-- 


-- 
-- Procedures
-- 
DELIMITER $$
-- 
$$

-- 
DELIMITER ;
-- 
