import { test, expect } from '@playwright/test';

const URL = 'https://practicetestautomation.com/practice-test-login/';

test.describe('Login Flow', () => {

  test('login exitoso con credenciales válidas', async ({ page }) => {
    await page.goto(URL);
    await page.fill('#username', 'student');
    await page.fill('#password', 'Password123');
    await page.click('#submit');
    await expect(page).toHaveURL(/logged-in-successfully/);
    await expect(page.locator('h1')).toContainText('Logged In Successfully');
  });

  test('login fallido con contraseña incorrecta', async ({ page }) => {
    await page.goto(URL);
    await page.fill('#username', 'student');
    await page.fill('#password', 'wrongpassword');
    await page.click('#submit');
    await expect(page.locator('#error')).toBeVisible();
    await expect(page.locator('#error')).toContainText('Your password is invalid!');
  });

  test('login fallido con usuario incorrecto', async ({ page }) => {
    await page.goto(URL);
    await page.fill('#username', 'wronguser');
    await page.fill('#password', 'Password123');
    await page.click('#submit');
    await expect(page.locator('#error')).toBeVisible();
    await expect(page.locator('#error')).toContainText('Your username is invalid!');
  });

});
