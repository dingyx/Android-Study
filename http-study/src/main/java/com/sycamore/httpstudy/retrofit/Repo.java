package com.sycamore.httpstudy.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author dingyx
 * @description: Repo
 * @date: 2022/5/12
 */
public class Repo {


    public Integer id;
    public String node_id;
    public String name;
    public String full_name;
    @SerializedName("private")
    public Boolean privateX;
    public OwnerBean owner;
    public String html_url;
    public String description;
    public Boolean fork;
    public String url;
    public String forks_url;
    public String keys_url;
    public String collaborators_url;
    public String teams_url;
    public String hooks_url;
    public String issue_events_url;
    public String events_url;
    public String assignees_url;
    public String branches_url;
    public String tags_url;
    public String blobs_url;
    public String git_tags_url;
    public String git_refs_url;
    public String trees_url;
    public String statuses_url;
    public String languages_url;
    public String stargazers_url;
    public String contributors_url;
    public String subscribers_url;
    public String subscription_url;
    public String commits_url;
    public String git_commits_url;
    public String comments_url;
    public String issue_comment_url;
    public String contents_url;
    public String compare_url;
    public String merges_url;
    public String archive_url;
    public String downloads_url;
    public String issues_url;
    public String pulls_url;
    public String milestones_url;
    public String notifications_url;
    public String labels_url;
    public String releases_url;
    public String deployments_url;
    public String created_at;
    public String updated_at;
    public String pushed_at;
    public String git_url;
    public String ssh_url;
    public String clone_url;
    public String svn_url;
    public Object homepage;
    public Integer size;
    public Integer stargazers_count;
    public Integer watchers_count;
    public String language;
    public Boolean has_issues;
    public Boolean has_projects;
    public Boolean has_downloads;
    public Boolean has_wiki;
    public Boolean has_pages;
    public Integer forks_count;
    public Object mirror_url;
    public Boolean archived;
    public Boolean disabled;
    public Integer open_issues_count;
    public Object license;
    public Boolean allow_forking;
    public Boolean is_template;
    public List<?> topics;
    public String visibility;
    public Integer forks;
    public Integer open_issues;
    public Integer watchers;
    public String default_branch;


    public static class OwnerBean {
        public String login;
        public Integer id;
        public String node_id;
        public String avatar_url;
        public String gravatar_id;
        public String url;
        public String html_url;
        public String followers_url;
        public String following_url;
        public String gists_url;
        public String starred_url;
        public String subscriptions_url;
        public String organizations_url;
        public String repos_url;
        public String events_url;
        public String received_events_url;
        public String type;
        public Boolean site_admin;
    }
}
