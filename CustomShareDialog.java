public static void doShare(Context context, Uri streamUri, String caption) {
		Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
		shareIntent.setType("image/*");

		List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(shareIntent, 0);
		List<Intent> targetedShareIntents = new ArrayList<Intent>();

		for (ResolveInfo resolveInfo : resInfoList) {
			String packageName = resolveInfo.activityInfo.packageName;
			Intent targetedShareIntent;
			if ("com.facebook.katana".equals(packageName)) {
				Intent i = new Intent(context, FacebookUploadActivity.class);
				PackageManager pm = context.getPackageManager();
				targetedShareIntent = new LabeledIntent(i, context.getApplicationContext().getPackageName(), resolveInfo.loadLabel(pm), R.drawable.com_facebook_icon);
			} else {
				targetedShareIntent = new Intent(shareIntent);
				targetedShareIntent.setPackage(packageName);
			}
			targetedShareIntent.setType("image/*");
			targetedShareIntent.putExtra(Intent.EXTRA_TEXT, caption);
			targetedShareIntent.putExtra(Intent.EXTRA_STREAM, streamUri);
			targetedShareIntents.add(targetedShareIntent);

		}
		Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(targetedShareIntents.size() - 1), "Share");

		chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[] {}));

		context.startActivity(chooserIntent);
	}
